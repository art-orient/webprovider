package by.art.webprovider.controller;

import by.art.webprovider.command.Command;
import by.art.webprovider.command.CommandType;
import by.art.webprovider.command.impl.EmptyCommand;
import by.art.webprovider.util.ErrorMessageManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static by.art.webprovider.command.AttributeConstant.CURRENT_PAGE;
import static by.art.webprovider.command.AttributeConstant.ERROR_CODE;
import static by.art.webprovider.command.AttributeConstant.ERROR_MESSAGE;
import static by.art.webprovider.command.CommandConstant.COMMAND;
import static by.art.webprovider.command.PagePath.ERROR_PAGE;

@WebServlet(urlPatterns = "/controller")
public class Controller extends HttpServlet {
  private static final Logger logger = LogManager.getLogger();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    logger.debug("Call of method doGet of class Controller");
    processRequest(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    logger.debug("Call of method doPost of class Controller");
    processRequest(req, resp);
  }

  private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    Command command = defineCommand(req);
    String page = command.execute(req);
    req.getSession().setAttribute(CURRENT_PAGE, page);
    if (page != null) {
      logger.debug("forward on page = {}", page);
      req.getRequestDispatcher(page).forward(req, resp);
    } else {
      req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);
    }
  }

  public static Command defineCommand(HttpServletRequest req) {
    String action = req.getParameter(COMMAND);
    logger.debug(() -> String.format("command action = %s", action));
    Command command;
    if (action == null || action.isEmpty()) {
      command = new EmptyCommand();
    } else {
      try {
        command = CommandType.valueOf(action.toUpperCase()).getCommand();
      } catch (IllegalArgumentException e) {
        command = new EmptyCommand();
        req.setAttribute(ERROR_CODE, ErrorMessageManager.getMessage("msg.errorCode404"));
        req.setAttribute(ERROR_MESSAGE, ErrorMessageManager.getMessage("msg.errorMessage404"));
        logger.warn(() -> String.format("Invalid action = %s", action));
      }
    }
    return command;
  }
}
