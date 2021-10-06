package commands;

import data.Dragon;
import utility.ConsoleColor;
import utility.ElementCreation;

import java.util.regex.Pattern;

/**
 * This class is responsible for the updating the element, id of which coincides with the entered id.
 */
public class CommandUpdate extends Command {
    private static final long serialVersionUID = 13L;

    public CommandUpdate() {
        super("update");
    }

    /**
     * Executes the command.
     *
     * @param enteredCommand the full name of the entered command
     * @return true if the entered command is correct; false otherwise
     */
    public boolean execute(String enteredCommand) {
        if (!checkCommand(enteredCommand)) {
            System.out.println(ConsoleColor.ANSI_RED.getColor() + "Команда не найдена. Введите \"help\" для справки" + ConsoleColor.ANSI_RESET.getColor());
            return false;
        } else return checkId(argument(enteredCommand));
    }


    /**
     * Checks whether String S is number or not.
     *
     * @param enteredCommand checked String
     * @return true if String S is a number; false otherwise
     */
    public boolean checkId(String enteredCommand) {
        boolean checkValue = false;
        try {
            long id = Long.parseLong(enteredCommand);
            if (id > 0) checkValue = true;
            else {
                System.out.println(ConsoleColor.ANSI_RED.getColor() + "Невозможно выполнить данную команду, так как id должен быть положительным" + ConsoleColor.ANSI_RESET.getColor());
            }
        } catch (NumberFormatException ex) {
            System.out.println(ConsoleColor.ANSI_RED.getColor() + "Команда не найдена. Введите \"help\" для справки" + ConsoleColor.ANSI_RESET.getColor());
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
        Pattern pattern = Pattern.compile("^update(\\s-?\\d+)$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(EnteredCommand).find();
    }

    @Override
    public Dragon execute(String[] fields, ElementCreation elementCreation, String username) {
        return elementCreation.createFromScript(fields, username);
    }

}
