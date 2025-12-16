package by.art.webprovider.command.impl;

import by.art.webprovider.command.Command;
import by.art.webprovider.util.PasswordEncryptor;
import by.art.webprovider.util.XssProtection;
import by.art.webprovider.exception.ServiceException;
import by.art.webprovider.model.entity.User;
import by.art.webprovider.model.entity.UserRole;
import by.art.webprovider.model.service.UserService;
import by.art.webprovider.util.ErrorMessageManager;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.art.webprovider.command.AttributeConstant.CONFIRM_PASSWORD;
import static by.art.webprovider.command.AttributeConstant.EMAIL;
import static by.art.webprovider.command.AttributeConstant.FIRSTNAME;
import static by.art.webprovider.command.AttributeConstant.LASTNAME;
import static by.art.webprovider.command.AttributeConstant.PASSWORD;
import static by.art.webprovider.command.AttributeConstant.REGISTRATION_STATUS;
import static by.art.webprovider.command.AttributeConstant.USERNAME;
import static by.art.webprovider.command.PagePath.CHECK_REG_STATUS_PAGE;

/**
 * The command is responsible for registering a new user
 *
 * @author Aliaksandr Artsikhovich
 * @see Command
 */
public class RegisterUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService;

    public RegisterUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        String username = XssProtection.replaceBrackets(req.getParameter(USERNAME));
        String password = req.getParameter(PASSWORD);
        String confirmPassword = req.getParameter(CONFIRM_PASSWORD);
        String firstname = XssProtection.replaceBrackets(req.getParameter(FIRSTNAME));
        String lastname = XssProtection.replaceBrackets(req.getParameter(LASTNAME));
        String email = XssProtection.replaceBrackets(req.getParameter(EMAIL));
        String registrationStatus;
        StringBuilder validationStatus = new StringBuilder();
        boolean isUsernameBusy = false;
        try {
            isUsernameBusy = userService.checkIsUsernameBusy(username, validationStatus);
        } catch (ServiceException e) {
            logger.warn(e);
        }
        if (userService.isValidUser(username, password, confirmPassword, firstname, lastname, email, validationStatus)
            && !isUsernameBusy) {
            User user = new User(username, firstname, lastname, email);
            String encryptedPassword = PasswordEncryptor.encryptPassword(password);
            user.setPassword(encryptedPassword);
            setRoleForClient(user);
            user.setActive(true);
            try {
                if (userService.registerUser(user)) {
                    registrationStatus = ErrorMessageManager.getMessage("msg.registrationSuccess");
                    logger.info("User registered successfully");
                } else {
                    registrationStatus = ErrorMessageManager.getMessage("msg.invalidData");
                    logger.info("Incorrect data entered, user was not registered");
                }
            } catch (ServiceException e) {
                logger.warn(e.getMessage(), e);
                registrationStatus = ErrorMessageManager.getMessage("msg.registrationError");
            }
        } else {
            registrationStatus = validationStatus.toString();
        }
        req.setAttribute(REGISTRATION_STATUS, registrationStatus);
        return CHECK_REG_STATUS_PAGE;
    }

    private void setRoleForClient(User user) {
        user.setRole(UserRole.CLIENT);
        try {
            if (userService.isFirstUser()) {
                user.setRole(UserRole.ADMIN);
            }
        } catch (ServiceException e) {
            logger.warn(e.getMessage(), e);
        }
    }
}
