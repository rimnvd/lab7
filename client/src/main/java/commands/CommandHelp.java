package commands;


import utility.ConsoleColor;

/**
 * This class is responsible for giving information about all the commands in the application.
 */
public class CommandHelp extends Command {
    private static final long serialVersionUID = 5L;

    public CommandHelp() {
        super("help");
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
            return false;
        }

    }

}
