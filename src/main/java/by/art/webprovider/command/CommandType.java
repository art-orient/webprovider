package by.art.webprovider.command;

import by.art.webprovider.command.impl.HomeCommand;

public enum CommandType {
//    LANGUAGE(new LanguageCommand()),
    HOME(new HomeCommand());
//    REGISTRATION(new RegistrationCommand()),
//    REGISTER_USER(new RegisterUserCommand(new UserServiceImpl())),
//    CHECK_REG_STATUS(new CheckRegStatusCommand()),
//    LOGIN(new LoginCommand()),
//    LOGIN_USER(new LoginUserCommand(new UserServiceImpl())),
//    LOGOUT(new LogoutCommand()),

    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
