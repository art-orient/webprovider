package by.art.webprovider.command.impl;

import by.art.webprovider.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.art.webprovider.command.AttributeConstant.CURRENT_PAGE;
import static by.art.webprovider.command.AttributeConstant.LANGUAGE;
import static by.art.webprovider.command.PagePath.INDEX_PAGE;

/**
 * The command is responsible for going to the home page
 *
 * @author Aliaksandr Artsikhovich
 * @see Command
 */
public class HomeCommand implements Command {
  private static final Logger logger = LogManager.getLogger();

  @Override
  public String execute(HttpServletRequest req) {
    req.getSession().setAttribute(CURRENT_PAGE, INDEX_PAGE);
    logger.debug("redirect to index page");
    return INDEX_PAGE;
  }
}
