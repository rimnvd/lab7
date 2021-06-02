package commands;


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
            System.out.println("\u001B[31m" + "Команда не найдена. Введите \"help\" для справки" + "\u001B[0m");
        }
        return false;
    }
}
