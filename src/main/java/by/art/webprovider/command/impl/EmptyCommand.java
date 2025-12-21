package by.art.webprovider.command.impl;

import by.art.webprovider.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.art.webprovider.command.RequestAttribute.CURRENT_PAGE;
import static by.art.webprovider.command.PagePath.ERROR_PAGE;

/**
 * The command is responsible for going to the error page for an empty or invalid command
 *
 * @author Aliaksandr Artsikhovich
 * @see Command
 */
public class EmptyCommand implements Command {
  private static final Logger logger = LogManager.getLogger();

  @Override
  public String execute(HttpServletRequest req) {
    logger.debug("redirect on error page");
    req.getSession().setAttribute(CURRENT_PAGE, ERROR_PAGE);
    return ERROR_PAGE;
  }
}
