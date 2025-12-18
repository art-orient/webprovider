package by.art.webprovider.command.impl;

import by.art.webprovider.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.art.webprovider.command.PagePath.INDEX_PAGE;

/**
 * The command is responsible for logging out the user and clearing his session
 *
 * @author Aliaksandr Artsikhovich
 * @see Command
 */
public class LogoutCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req) {
        req.getSession().invalidate();
        logger.log(Level.INFO, "User is logout");
        return INDEX_PAGE;
    }
}
