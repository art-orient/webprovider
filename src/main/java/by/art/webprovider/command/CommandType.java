package by.art.webprovider.command;

import by.art.webprovider.command.impl.CheckRegStatusCommand;
import by.art.webprovider.command.impl.HomeCommand;
import by.art.webprovider.command.impl.LanguageCommand;
import by.art.webprovider.command.impl.RegisterUserCommand;
import by.art.webprovider.model.service.impl.UserServiceImpl;

public enum CommandType {
  LANGUAGE(new LanguageCommand()),
  HOME(new HomeCommand()),
  CHECK_REG_STATUS(new CheckRegStatusCommand()),
  REGISTER_USER(new RegisterUserCommand(new UserServiceImpl()));

  private final Command command;

  CommandType(Command command) {
    this.command = command;
  }

  public Command getCommand() {
    return command;
  }
}
