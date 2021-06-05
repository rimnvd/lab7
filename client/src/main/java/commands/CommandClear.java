package commands;


import utility.ConsoleColor;

/**
 * This class is responsible for the removing all of the elements from the collection.
 */
public class CommandClear extends Command {
    private static final long serialVersionUID = 3L;

    public CommandClear() {
        super("clear");
    }

    /**
     * Executes the command.
     *
     * @param enteredCommand the full name of the entered command
     * @return true if data is correct; false otherwise
     */

    @Override
    public boolean execute(String enteredCommand) {
        if (checkCommand(enteredCommand)) {
            return true;
        } else {
            System.out.println(ConsoleColor.ANSI_RED.getColor() + "Команда не найдена. Введите \"help\" для справки" + ConsoleColor.ANSI_RESET.getColor());
        }
        return false;
    }
}
