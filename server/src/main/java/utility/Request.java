package utility;

import commands.Command;
import data.Dragon;

import java.io.Serializable;

public class Request implements Serializable {
    private final Dragon dragon;
    private final Command commandName;
    private final String fullCommand;
    private static final long serialVersionUID = 30L;

    public Request(Dragon dragon, Command commandName, String fullCommand) {
        this.dragon = dragon;
        this.commandName = commandName;
        this.fullCommand = fullCommand;
    }

    public Request(Command commandName, String fullCommand) {
        this.dragon = null;
        this.commandName = commandName;
        this.fullCommand = fullCommand;
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


}
