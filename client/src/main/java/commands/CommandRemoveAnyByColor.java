package commands;

import data.Color;

import java.util.regex.Pattern;

/**
 * This class is responsible for the removing one element from the the collection, color of which
 * coincides with the entered color.
 */
public class CommandRemoveAnyByColor extends Command {
    private static final long serialVersionUID = 8L;

    public CommandRemoveAnyByColor() {
        super("remove_any_by_color");
    }

    /**
     * Executes the command.
     *
     * @param enteredCommand the full name of the entered command
     * @return
     */
    @Override
    public boolean execute(String enteredCommand) {
        if (!checkCommand(enteredCommand)) {
            System.out.println("Команда не найдена. Введите \"help\" для справки");
            return false;
        }
        return checkColor(argument(enteredCommand).toUpperCase());
    }


    /**
     * Checks whether the name of the argument is right or not.
     *
     * @param enteredCommand the full name of the entered command
     * @return true if the name is not right; false otherwise
     */
    @Override
    public boolean checkCommand(String enteredCommand) {
        Pattern pattern = Pattern.compile("^remove_any_by_color(\\s\\w+)$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(enteredCommand).find();
    }

    public boolean checkColor(String enteredCommand) {
        boolean checkValue = true;
        try {
            Color.valueOf(enteredCommand);
        } catch (IllegalArgumentException ex) {
            System.out.println("Команда не найдена. Введите \"help\" для справки");
            checkValue = false;
        }
        return checkValue;
    }
}
