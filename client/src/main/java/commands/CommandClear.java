package commands;


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
     * @return
     */

    @Override
    public boolean execute(String enteredCommand) {
        if (checkCommand(enteredCommand)) {
            return true;
        } else {
            System.out.println("\u001B[31m" + "Команда не найдена. Введите \"help\" для справки" + "\u001B[31m");
        }
        return false;
    }
}
