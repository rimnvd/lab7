package commands;



/**
 * This class is responsible for giving information about the elements in the collection.
 */
public class CommandShow extends Command {
    private static final long serialVersionUID = 12L;

    public CommandShow() {
        super("show");
    }

    /**
     * Executes the command.
     *
     * @param EnteredCommand the full name of the entered command
     * @return
     */
    public boolean execute(String EnteredCommand) {
        if (checkCommand(EnteredCommand)) {
            return true;
        } else {
            System.out.println("Команда не найдена. Введите \"help\" для справки");
            return false;
        }
    }
}
