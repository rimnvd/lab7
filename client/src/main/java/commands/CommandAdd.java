package commands;


import data.Dragon;
import utility.ConsoleColor;
import utility.ElementCreation;

/**
 * This class is responsible for the adding an element to the collection.
 */
public class CommandAdd extends Command {
    private static final long serialVersionUID = 1L;

    public CommandAdd() {
        super("add");

    }

    @Override
    public boolean execute(String enteredCommand) {
        if (!checkCommand(enteredCommand)) {
            System.out.println(ConsoleColor.ANSI_RED.getColor() + "Команда не найдена. Введите \"help\" для справки" + ConsoleColor.ANSI_RESET.getColor());
            return false;
        }
        return true;
    }

    @Override
    public Dragon execute(String[] fields, ElementCreation elementCreation) {
        return elementCreation.createFromScript(fields);
    }

}


