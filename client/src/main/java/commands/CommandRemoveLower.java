package commands;

import data.Dragon;
import utility.ElementCreation;


/**
 * This class is responsible for the removing all the elements from the collection that are lower than the entered element.
 */
public class CommandRemoveLower extends Command {
    private static final long serialVersionUID = 11L;

    public CommandRemoveLower() {
        super("remove_lower");
    }

    /**
     * Executes the command.
     *
     * @param enteredCommand The full name of the command.
     * @return true if data is correct; false otherwise
     */
    @Override
    public boolean execute(String enteredCommand) {
        if (!checkCommand(enteredCommand)) {
            System.out.println("\u001B[31m" + "Команда не найдена. Введите \"help\" для справки" + "\u001B[0m");
            return false;
        }
        return true;
    }

    @Override
    public Dragon execute(String[] fields, ElementCreation elementCreation) {
        return elementCreation.createFromScript(fields);
    }
}

