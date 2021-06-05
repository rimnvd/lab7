package commands;


import utility.ConsoleColor;

/**
 * This class is responsible for the completing the programme.
 */
public class CommandExit extends Command {

    public CommandExit() {
        super("exit");
    }


    @Override
    public boolean execute(String EnteredCommand) {
        if (checkCommand(EnteredCommand)) {
            System.out.println("Завершение работы клиентского приложения");
            System.exit(0);
        } else {
            System.out.println(ConsoleColor.ANSI_RED.getColor() + "Команда не найдена. Введите \"help\" для справки" + ConsoleColor.ANSI_RESET.getColor());
        }
        return false;
    }
}
