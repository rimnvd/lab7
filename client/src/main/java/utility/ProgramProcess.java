package utility;

import commands.*;
import data.Dragon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class is responsible for the application process.
 */
public class ProgramProcess {
    private final HashMap<String, Command> commands = new HashMap<>();
    private final ElementCreation elementCreation;
    private final ArrayList<String> path = new ArrayList<>();
    private final Scanner scanner;
    private Client client;

    public ProgramProcess(ElementCreation elementCreation, Scanner scanner, Client client) {
        this.elementCreation = elementCreation;
        this.scanner = scanner;
        this.client = client;
        commands.put("add", new CommandAdd());
        commands.put("add_if_max", new CommandAddIfMax());
        commands.put("clear", new CommandClear());
        commands.put("execute_script", new CommandExecuteScript(path, this, elementCreation));
        commands.put("exit", new CommandExit());
        commands.put("group_counting_by_character", new CommandGroupCountingByCharacter());
        commands.put("help", new CommandHelp());
        commands.put("info", new CommandInfo());
        commands.put("print_descending", new CommandPrintDescending());
        commands.put("remove_any_by_color", new CommandRemoveAnyByColor());
        commands.put("remove_by_id", new CommandRemoveById());
        commands.put("remove_last", new CommandRemoveLast());
        commands.put("remove_lower", new CommandRemoveLower(elementCreation));
        commands.put("save", new CommandSave());
        commands.put("show", new CommandShow());
        commands.put("update", new CommandUpdate());
    }

    /**
     * Asks to enter the name of the command and executes necessary command.
     */
    public void process() {
        System.out.println();
        System.out.println("Программа запущена в интерактивном режиме. Введите \"help\", чтобы посмотреть доступные команды");
        try {
            while (true) {
                Request request;
                String line = scanner.nextLine().trim().toLowerCase();
                System.out.println();
                if (commands.containsKey(new Command().commandName(line))) {
                    if (new Command().commandName(line).equals("execute_script")) {
                        commands.get(new Command().commandName(line)).execute(line);
                    } else {
                        if (commands.get(new Command().commandName(line)).execute(line)) {
                            Dragon dragon;
                            if (new Command().commandName(line).equals("add") || new Command().commandName(line).equals("add_if_max") || new Command().commandName(line).equals("update")) {
                                dragon = elementCreation.createElement();
                                request = new Request(dragon, commands.get(new Command().commandName(line)), line);
                            } else request = new Request(commands.get(new Command().commandName(line)), line);
                            run(request);
                        }
                    }
                } else {
                    System.out.println("Команда не найдена. Введите \"help\" для справки");
                }
                System.out.println();
                System.out.println("Введите команду");
            }
        } catch (NoSuchElementException ex) {
            System.out.println();
            System.out.println("Завершение работы программы");
            System.exit(0);
        }
    }

    public void run(Request request) {
        client.connect();
        client.send(request);
        try {
            Response response = client.receive();
            System.out.println(response.getResponse());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }

}
