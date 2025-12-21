package by.art.webprovider.controller;

import by.art.webprovider.command.Command;
import by.art.webprovider.command.CommandType;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static by.art.webprovider.command.RequestAttribute.CURRENT_PAGE;
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
    Command command = CommandType.defineCommand(req);
    String page = command.execute(req);
    req.getSession().setAttribute(CURRENT_PAGE, page);
    if (page != null) {
      logger.debug("forward on page = {}", page);
      req.getRequestDispatcher(page).forward(req, resp);
    } else {
      req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);
    }
  }
}
