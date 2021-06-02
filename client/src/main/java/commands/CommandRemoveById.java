package commands;


import java.util.regex.Pattern;

/**
 * This class is responsible for the removing one element from the collection, id of which
 * coincides with the entered color.
 */
public class CommandRemoveById extends Command {
    private static final long serialVersionUID = 9L;

    public CommandRemoveById() {
        super("remove_by_id");
    }

    @Override
    public boolean execute(String enteredCommand) {
        if (!checkCommand(enteredCommand)) {
            System.out.println("\u001B[31m" + "Команда не найдена. Введите \"help\" для справки" + "\u001B[0m");
            return false;
        }
        return checkId(argument(enteredCommand));
    }

    /**
     * Checks whether String S is number or not.
     *
     * @param enteredCommand checked String
     * @return true if String S is a number; false otherwise
     */
    public boolean checkId(String enteredCommand) {
        boolean checkValue;
        try {
            Long.parseLong(enteredCommand);
            checkValue = true;
        } catch (NumberFormatException ex) {
            System.out.println("\u001B[31m" + "Команда не найдена. Введите \"help\" для справки" + "\u001B[0m");
            checkValue = false;
        }
        return checkValue;
    }

    /**
     * Checks whether the name of the argument is right or not.
     *
     * @param EnteredCommand the full name of the entered command
     * @return true if the name is not right; false otherwise
     */
    @Override
    public boolean checkCommand(String EnteredCommand) {
        Pattern pattern = Pattern.compile("^remove_by_id(\\s\\d+)$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(EnteredCommand).find();
    }

}
