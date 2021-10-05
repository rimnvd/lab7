package utility;

import commands.*;
import data.Dragon;
import exceptions.ServerUnavailableException;

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
    private final Client client;
    private final Authorization authorization;
    private Session session;

    public ProgramProcess(ElementCreation elementCreation, Scanner scanner, Client client, Authorization authorization) {
        this.elementCreation = elementCreation;
        this.scanner = scanner;
        this.client = client;
        this.authorization = authorization;
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
        commands.put("remove_lower", new CommandRemoveLower());
        commands.put("show", new CommandShow());
        commands.put("update", new CommandUpdate());
    }

    /**
     * Asks to enter the name of the command and executes necessary command.
     */
    public void process() {
        try {
            System.out.println();
            if (authorization.checkRegistration()) {
                authorization.authorize();
            } else {
                authorization.register();
            }
            session = authorization.getSession();
            System.out.println();
            System.out.println("Программа запущена в интерактивном режиме. Введите \"help\", чтобы посмотреть доступные команды");
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
                            if (new Command().commandName(line).equals("add") || new Command().commandName(line).equals("add_if_max")
                                    || new Command().commandName(line).equals("update") || new Command().commandName(line).equals("remove_lower")) {
                                dragon = elementCreation.createElement(session.getUsername());
                                request = new Request(dragon, commands.get(new Command().commandName(line)), line, session.getUsername(), session.getPassword(), true);
                            } else
                                request = new Request(null, commands.get(new Command().commandName(line)), line, session.getUsername(), session.getPassword(), true);
                            run(request);
                        }
                    }
                } else {
                    System.out.println(ConsoleColor.ANSI_RED.getColor() + "Команда не найдена. Введите \"help\" для справки" + ConsoleColor.ANSI_RESET.getColor());
                }
                System.out.println();
                System.out.println("Введите команду");
            }
        } catch (NoSuchElementException ex) {
            System.out.println();
            System.out.println("Завершение работы клиентского приложения");
            System.exit(0);
        }
    }

    public void run(Request request) {
        try {
            client.connect();
            client.send(request);
            Response response = client.receive();
            client.getResponse(response);
        } catch (IOException | ClassNotFoundException | ServerUnavailableException e) {
            System.out.println(ConsoleColor.ANSI_RED.getColor() + "Ошибка подкдючения к северу" + ConsoleColor.ANSI_RESET.getColor());
        }
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }

}
