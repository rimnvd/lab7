package commands;


import data.Dragon;
import utility.ElementCreation;

/**
 * This class is responsible for the adding an element to the collection
 * if this element is greater than the max element in the collection.
 */
public class CommandAddIfMax extends Command {
    private static final long serialVersionUID = 2L;

    public CommandAddIfMax() {
        super("add_if_max");

    }

    /**
     * Executes the command.
     *
     * @param enteredCommand the full name of the entered command
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
