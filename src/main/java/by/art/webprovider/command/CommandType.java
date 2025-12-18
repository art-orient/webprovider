package by.art.webprovider.command;

import by.art.webprovider.command.impl.CheckRegStatusCommand;
import by.art.webprovider.command.impl.HomeCommand;
import by.art.webprovider.command.impl.ChangeLanguageCommand;
import by.art.webprovider.command.impl.LoginCommand;
import by.art.webprovider.command.impl.LoginUserCommand;
import by.art.webprovider.command.impl.LogoutCommand;
import by.art.webprovider.command.impl.RegisterUserCommand;
import by.art.webprovider.model.service.impl.UserServiceImpl;

public enum CommandType {
  CHANGE_LANGUAGE(new ChangeLanguageCommand()),
  HOME(new HomeCommand()),
  CHECK_REG_STATUS(new CheckRegStatusCommand()),
  REGISTER_USER(new RegisterUserCommand(new UserServiceImpl())),
  LOGIN(new LoginCommand()),
  LOGIN_USER(new LoginUserCommand(new UserServiceImpl())),
  LOGOUT(new LogoutCommand());

  private final Command command;

  CommandType(Command command) {
    this.command = command;
  }

  public Command getCommand() {
    return command;
  }
}
