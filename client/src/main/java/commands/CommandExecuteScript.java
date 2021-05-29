package commands;

import data.Dragon;
import utility.ElementCreation;
import utility.ProgramProcess;
import utility.Request;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * This class is responsible for the executing commands from the script.
 */
public class CommandExecuteScript extends Command {
    private final ArrayList<String> path;
    private final ProgramProcess programProcess;
    private final ElementCreation elementCreation;

    public CommandExecuteScript(ArrayList<String> path, ProgramProcess programProcess, ElementCreation elementCreation) {
        super("execute_script");
        this.path = path;
        this.programProcess = programProcess;
        this.elementCreation = elementCreation;
    }


    @Override
    public boolean execute(String enteredCommand) {
        if (checkCommand(enteredCommand)) {
            File file = new File(argument(enteredCommand));
            if (file.exists() && !file.canRead()) {
                System.out.println("Невозможно выполнить данную команду, так как у указанного файла отсутвуют права на чтение");
            } else {
                try {
                    int data = 0;
                    InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
                    if (path.contains(file.getAbsolutePath())) {
                        data = -1;
                        System.out.println("Невозможно выполнить команду " + enteredCommand);
                    } else {
                        path.add((file).getAbsolutePath());
                    }
                    StringBuilder CommandName = new StringBuilder();
                    while (data != -1) {
                        data = reader.read();
                        if (data != 10 && data != -1) {
                            CommandName.append((char) data);
                        } else {
                            String fullCommandName = CommandName.toString().trim().toLowerCase();
                            if (fullCommandName.equals("add")) {
                                System.out.println(fullCommandName);
                                System.out.println();
                                Dragon dragon = programProcess.getCommands().get("add").execute(fieldValues(reader), elementCreation);
                                if (dragon != null) {
                                    programProcess.run(new Request(dragon, programProcess.getCommands().get("add"), fullCommandName));
                                } else System.out.println("Элемент не может быть добавлен в коллекцию\n");
                            } else if (fullCommandName.equals("add_if_max")) {
                                System.out.println(fullCommandName);
                                System.out.println();
                                Dragon dragon = programProcess.getCommands().get("add_if_max").execute(fieldValues(reader), elementCreation);
                                if (dragon != null) {
                                    programProcess.run(new Request(dragon, programProcess.getCommands().get("add"), fullCommandName));
                                } else System.out.println("Элемент не может быть добавлен в коллекцию\n");
                            } else if (new Command().commandName(fullCommandName).equals("update")) {
                                try {
                                    Long id = Long.parseLong(new Command().argument(fullCommandName));
                                    System.out.println(fullCommandName);
                                    System.out.println();
                                    if (id <= 0) {
                                        System.out.println("Команда " + fullCommandName + " не найдена");
                                    } else {
                                        Dragon dragon = programProcess.getCommands().get("update").execute(fieldValues(reader), elementCreation);
                                        if (dragon != null) {
                                            programProcess.run(new Request(dragon, programProcess.getCommands().get("update"), fullCommandName));
                                        } else {
                                            System.out.println("Элемент не может быть добавлен в коллекцию\n");
                                        }
                                    }
                                } catch (NumberFormatException ex) {
                                    System.out.println("Команда " + fullCommandName + " не найдена");
                                    System.out.println();
                                }
                            } else if (programProcess.getCommands().containsKey(new Command().commandName(fullCommandName))) {
                                System.out.println(fullCommandName);
                                System.out.println();
                                programProcess.run(new Request(null, programProcess.getCommands().get(new Command().commandName(fullCommandName)), fullCommandName));
                                System.out.println();
                            } else if (fullCommandName.length() != 0) {
                                System.out.println("Команда " + fullCommandName + " не найдена");
                                System.out.println();
                            }
                            CommandName.delete(0, CommandName.length());
                            if (data == -1) {
                                path.remove(path.size() - 1);
                                System.out.println("Выполнение скрипта " + argument(enteredCommand) + " завершено");
                            }
                        }
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("Указанный файл не найден");
                } catch (IOException e) {
                    System.out.println("Ошибка ввода-вывода");
                }
            }
        } else {
            System.out.println("Команда не найдена. Введите \"help\" для справки");
        }
        return true;
    }

    /**
     * Checks whether the name of the argument is right or not.
     *
     * @param EnteredCommand the full name of the entered command.
     * @return true if the name is not right; false otherwise
     */
    @Override
    public boolean checkCommand(String EnteredCommand) {
        Pattern pattern = Pattern.compile("^execute_script(\\s\\S+)$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(EnteredCommand).find();
    }

    public String[] fieldValues(InputStreamReader reader) throws IOException {
        String[] fieldValue = new String[9];
        StringBuilder value = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            int data = reader.read();
            while (data != 10 && data != -1) {
                value.append((char) data);
                data = reader.read();
            }
            String Value = value.toString().trim();
            fieldValue[i] = Value;
            value.delete(0, value.length());
        }
        return fieldValue;
    }


}
