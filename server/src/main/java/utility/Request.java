package utility;

import commands.Command;
import data.Dragon;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 30L;
    private final Dragon dragon;
    private final Command commandName;
    private final String fullCommand;
    private final String username;
    private final String password;
    private final boolean isRegister;

    public Request(Dragon dragon, Command commandName, String fullCommand, String username, String password, boolean isRegister) {
        this.dragon = dragon;
        this.commandName = commandName;
        this.fullCommand = fullCommand;
        this.username = username;
        this.password = password;
        this.isRegister = isRegister;
    }

    public Command getCommandName() {
        return commandName;
    }

    public Dragon getDragon() {
        return dragon;
    }

    public String getFullCommand() {
        return fullCommand;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean getIsRegister() {
        return isRegister;
    }


}
