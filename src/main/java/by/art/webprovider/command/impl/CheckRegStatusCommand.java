package by.art.webprovider.command.impl;

import by.art.webprovider.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.art.webprovider.command.AttributeConstant.REGISTRATION_STATUS;
import static by.art.webprovider.command.PagePath.REGISTRATION_PAGE;

/**
 * The command is responsible for creating the registration status when registering a new user
 *
 * @author Aliaksandr Artsikhovich
 * @see Command
 */
public class CheckRegStatusCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req) {
        String registrationStatus = req.getParameter(REGISTRATION_STATUS);
        if (registrationStatus == null) {
            registrationStatus = "";
        }
        req.getSession().setAttribute(REGISTRATION_STATUS, registrationStatus);
        logger.debug("Created registration status");
        return REGISTRATION_PAGE;
    }
}
