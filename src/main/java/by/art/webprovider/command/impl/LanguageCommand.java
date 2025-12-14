package by.art.webprovider.command.impl;

import by.art.webprovider.command.Command;
import by.art.webprovider.command.CommandFactory;
import by.art.webprovider.util.LocaleManager;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.art.webprovider.command.AttributeConstant.CURRENT_PAGE;
import static by.art.webprovider.command.AttributeConstant.LANGUAGE;
import static by.art.webprovider.command.PagePath.ERROR_PAGE;
import static by.art.webprovider.command.PagePath.INDEX_PAGE;

/**
 * The command is responsible for changing the interface language
 *
 * @author Aliaksandr Artsikhovich
 * @see Command
 */
public class LanguageCommand implements Command {
  private static final Logger logger = LogManager.getLogger();

  @Override
  public String execute(HttpServletRequest req) {
    String selectedLanguage = req.getParameter(LANGUAGE);
    req.getSession().setAttribute(LANGUAGE, selectedLanguage);
    logger.atDebug().log(String.format("change of language %s", selectedLanguage));
    LocaleManager.setLocale(selectedLanguage);
    String page = (String) req.getSession().getAttribute(CURRENT_PAGE);
    if (ERROR_PAGE.equals(page)) {
      CommandFactory.sendPageNotFound(req);
    }
    page = INDEX_PAGE;
    return page;
  }
}
