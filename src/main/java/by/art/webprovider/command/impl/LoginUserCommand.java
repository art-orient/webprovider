package by.art.webprovider.command.impl;

import by.art.webprovider.command.Command;
import by.art.webprovider.exception.ServiceException;
import by.art.webprovider.model.entity.User;
import by.art.webprovider.model.service.UserService;
import by.art.webprovider.util.ErrorMessageManager;
import by.art.webprovider.util.PasswordEncryptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.art.webprovider.command.RequestAttribute.ERROR;
import static by.art.webprovider.command.PagePath.INDEX_PAGE;
import static by.art.webprovider.command.PagePath.LOGIN_PAGE;
import static by.art.webprovider.command.RequestParameter.LANGUAGE;
import static by.art.webprovider.command.RequestParameter.PASSWORD;
import static by.art.webprovider.command.RequestParameter.USERNAME;
import static by.art.webprovider.model.dao.column.UserColumn.ROLE;

/**
 * The command is responsible for user authentication
 *
 * @author Aliaksandr Artsikhovich
 * @see Command
 */
public class LoginUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService;

    public LoginUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        req.getSession().removeAttribute(ERROR);
        String page = LOGIN_PAGE;
        String username = req.getParameter(USERNAME);
        String password = req.getParameter(PASSWORD);
        String encryptedPassword = PasswordEncryptor.encryptPassword(password);
        HttpSession session = req.getSession();
        String language = (String) session.getAttribute(LANGUAGE);
        try {
            boolean[] validData = userService.validateCredentialsAndActivity(username, encryptedPassword);
            boolean isValidCredentials = validData[0];
            boolean isActive = validData[1];
            if (isValidCredentials) {
                if (!isActive) {
                    req.setAttribute(ERROR, ErrorMessageManager.getMessage("msg.accessDenied"));
                    logger.log(Level.INFO, "User {} Access denied, account blocked", username);
                    return page;
                }
                session.invalidate();
                session = req.getSession();
                session.setAttribute(LANGUAGE, language);
                session.setAttribute(USERNAME, username);
                setUserRole(session, username);
                page = INDEX_PAGE;
            } else {
                if (username != null) {
                    req.setAttribute(ERROR, ErrorMessageManager.getMessage("msg.invalidCredentials"));
                    logger.warn("Invalid credentials");
                }
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
            page = INDEX_PAGE;
        }
        return page;
    }

    private void setUserRole (HttpSession session, String username) {
        try {
            Optional<User> optionalUser = userService.findUserByUsername(username);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                session.setAttribute(ROLE, user.getRole().name());
                logger.log(Level.INFO, "User successfully logged in");
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
    }
}