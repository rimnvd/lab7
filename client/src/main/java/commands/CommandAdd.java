package commands;


import data.Dragon;
import utility.ElementCreation;

/**
 * This class is responsible for the adding an element to the collection.
 */
public class CommandAdd extends Command  {
    private static final long serialVersionUID = 1L;

    public CommandAdd() {
        super("add");

    }

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


