package commands;


import utility.ConsoleColor;

/**
 * This class is responsible for printing the collection in descending order.
 */
public class CommandPrintDescending extends Command {
    private static final long serialVersionUID = 7L;

    public CommandPrintDescending() {
        super("print_descending");
    }

    /**
     * Executes the command.
     *
     * @param enteredCommand the name of the specified command
     * @return true if data is correct; false otherwise
     */
    @Override
    public boolean execute(String enteredCommand) {
        if (checkCommand(enteredCommand)) {
            return true;
        } else {
            System.out.println(ConsoleColor.ANSI_RED.getColor() + "Команда не найдена. Введите \"help\" для справки" + ConsoleColor.ANSI_RESET.getColor());
            return false;
        }
    }
}
