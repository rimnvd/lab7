package commands;


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
            System.out.println("\u001B[31m" + "Команда не найдена. Введите \"help\" для справки" + "\u001B[0m");
            return false;
        }
    }
}
