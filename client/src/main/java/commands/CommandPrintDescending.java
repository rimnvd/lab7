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
     * @return
     */
    @Override
    public boolean execute(String enteredCommand) {
        if (checkCommand(enteredCommand)) {
            return true;
        } else {
            System.out.println("Команда не найдена. Введите \"help\" для справки");
            return false;
        }
    }
}
