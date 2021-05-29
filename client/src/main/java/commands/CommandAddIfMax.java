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
     * @param EnteredCommand the full name of the entered command
     * @return
     */
    @Override
    public boolean execute(String EnteredCommand) {
        if (!checkCommand(EnteredCommand)) {
            System.out.println("\u001B[31m" + "Команда не найдена. Введите \"help\" для справки" + "\u001B[31m");
            return true;
        }
        return false;
    }

    @Override
    public Dragon execute(String[] fields, ElementCreation elementCreation) {
        return elementCreation.createFromScript(fields);
    }

}
