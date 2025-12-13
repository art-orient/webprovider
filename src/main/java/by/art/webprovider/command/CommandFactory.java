package by.art.webprovider.command;

import by.art.webprovider.command.impl.EmptyCommand;
import by.art.webprovider.util.ErrorMessageManager;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.art.webprovider.command.AttributeConstant.ERROR_CODE;
import static by.art.webprovider.command.AttributeConstant.ERROR_MESSAGE;
import static by.art.webprovider.command.CommandConstant.COMMAND;

public class CommandFactory {
    static Logger logger = LogManager.getLogger();

    private CommandFactory() {
    }

    public static Command defineCommand(HttpServletRequest req) {
        String action = req.getParameter(COMMAND);
        logger.debug(() -> String.format("command action = %s", action));
        Command command;
        if (action == null || action.isEmpty()) {
            command = new EmptyCommand();
            sendPageNotFound(req);
        } else {
            try {
                command = CommandType.valueOf(action.toUpperCase()).getCommand();
            } catch (IllegalArgumentException e) {
                command = new EmptyCommand();
                logger.warn(() -> String.format("Invalid action = %s", action));
                sendPageNotFound(req);
            }
        }
        return command;
    }

    public static void sendPageNotFound(HttpServletRequest req) {
        req.setAttribute(ERROR_CODE, ErrorMessageManager.getMessage("msg.errorCode404"));
        req.setAttribute(ERROR_MESSAGE, ErrorMessageManager.getMessage("msg.errorMessage404"));
        logger.debug(() -> "set attributes for Error 404");
    }
}
