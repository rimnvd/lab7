package commands;




/**
 * This class is responsible for giving information about the collection.
 */
public class CommandInfo extends Command {
    private static final long serialVersionUID = 6L;


    public CommandInfo() {
        super("info");
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
            System.out.println("Команда не найдена. Введите \"help\" для справки");
            return false;
        }
    }
}

