package by.art.webprovider.command;

import by.art.webprovider.command.impl.CheckRegStatusCommand;
import by.art.webprovider.command.impl.EmptyCommand;
import by.art.webprovider.command.impl.HomeCommand;
import by.art.webprovider.command.impl.ChangeLanguageCommand;
import by.art.webprovider.command.impl.LoginPageCommand;
import by.art.webprovider.command.impl.LoginUserCommand;
import by.art.webprovider.command.impl.LogoutCommand;
import by.art.webprovider.command.impl.RegisterUserCommand;
import by.art.webprovider.model.service.impl.UserServiceImpl;
import by.art.webprovider.util.ErrorMessageManager;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.art.webprovider.command.RequestAttribute.ERROR_CODE;
import static by.art.webprovider.command.RequestAttribute.ERROR_MESSAGE;
import static by.art.webprovider.command.RequestParameter.COMMAND;

public enum CommandType {
  CHANGE_LANGUAGE(new ChangeLanguageCommand()),
  HOME(new HomeCommand()),
  CHECK_REG_STATUS(new CheckRegStatusCommand()),
  REGISTER_USER(new RegisterUserCommand(new UserServiceImpl())),
  LOGIN_PAGE(new LoginPageCommand()),
  LOGIN_USER(new LoginUserCommand(new UserServiceImpl())),
  LOGOUT(new LogoutCommand());

  private static final Logger logger = LogManager.getLogger();
  private final Command command;

  CommandType(Command command) {
    this.command = command;
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

  public Command getCommand() {
    return command;
  }
}
