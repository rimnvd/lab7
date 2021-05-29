package commands;


/**
 * This class is responsible for saving the collection to the file.
 */
public class CommandSave extends Command {

    public CommandSave() {
        super("save");
    }

    /**
     * Executes the command.
     *
     * @param EnteredCommand The full name of the entered command.
     * @return
     */
    @Override
    public boolean execute(String EnteredCommand) {
        if (checkCommand(EnteredCommand)) {
            return true;
        } else {
            System.out.println("Команда не найдена. Введите \"help\" для справки");
            return false;
        }
    }
}
