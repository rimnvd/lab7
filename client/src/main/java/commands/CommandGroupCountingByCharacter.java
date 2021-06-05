package commands;


import utility.ConsoleColor;

/**
 * This class is responsible for the grouping elements of the collection by character
 * and counting the number of the elements in each group.
 */
public class CommandGroupCountingByCharacter extends Command {
    private static final long serialVersionUID = 4L;

    public CommandGroupCountingByCharacter() {
        super("group_counting_by_character");
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
