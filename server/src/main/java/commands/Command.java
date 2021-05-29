package commands;


import data.Dragon;
import utility.Response;

import java.io.Serializable;

/**
 * General class, superclass for the all classes from the package commands.
 */
public class Command implements Serializable {
    private String name;
    private static final long serialVersionUID = 0L;


    public Command(String name) {
        this.name = name;
    }

    public Command() {

    }

    public String getName() {
        return name;
    }

    public Response execute(String enteredCommand, Dragon dragon) {
        return new Response();
    }


    /**
     * Checks whether the name of the entered command is right or not.
     *
     * @param enteredCommand the full name of the entered command
     * @return true if the name is right; false otherwise
     */
    public boolean checkCommand(String enteredCommand) {
        return enteredCommand.equals(name);
    }

    /**
     * Returns the name of the argument for the commands which have the argument.
     *
     * @param enteredCommand the full name of the entered command
     * @return the name of the argument for the commands which have the argument
     */
    public String argument(String enteredCommand) {
        return enteredCommand.substring(enteredCommand.indexOf(" ") + 1);
    }

    /**
     * Returns the name of the entered command without argument (regardless of the command).
     *
     * @param enteredCommand the full name of the entered command
     * @return the name of the command without argument
     */
    public String commandName(String enteredCommand) {
        if (!enteredCommand.contains(" ")) {
            return enteredCommand;
        } else return enteredCommand.substring(0, enteredCommand.indexOf(" "));
    }

    public void execute(String[] fields) {
    }

    public void execute(String[] fields, Long value) {
    }




}
