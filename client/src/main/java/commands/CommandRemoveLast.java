package commands;


import utility.ConsoleColor;

/**
 * This class is responsible for the removing the last element from the collection.
 */
public class CommandRemoveLast extends Command {
    private static final long serialVersionUID = 10L;

    public CommandRemoveLast() {
        super("remove_last");
    }


    /**
     * Executes the command.
     *
     * @param EnteredCommand the full name of the entered command
     * @return true if data is correct; false otherwise
     */
    @Override
    public boolean execute(String EnteredCommand) {
        if (checkCommand(EnteredCommand)) {
            return true;
        } else {
            System.out.println(ConsoleColor.ANSI_RED.getColor() + "Команда не найдена. Введите \"help\" для справки" + ConsoleColor.ANSI_RESET.getColor());
            return false;
        }
    }
}
