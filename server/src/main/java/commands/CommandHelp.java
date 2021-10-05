package commands;


import data.Dragon;
import utility.Response;

import java.util.ArrayList;
import java.util.HashMap;

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
     */
    @Override
    public Response execute(String enteredCommand, Dragon dragon, String username) {
        HashMap<String, String> commands = new HashMap<>();
        commands.put("help", "вывести справку по доступным командам");
        commands.put("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        commands.put("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        commands.put("add {element}", "добавить новый элемент в коллекцию");
        commands.put("update id {element}", "обновить значение элемента коллекции, id которого равен заданному");
        commands.put("remove_by_id id", "удалить элемент из коллекции по его id");
        commands.put("clear", "очистить коллекцию");
        commands.put("execute_script file_name", "считать и исполнить скрипт из указанного файла.");
        commands.put("exit", "завершить работу клиентского приложения");
        commands.put("remove_last", "удалить последний элемент из коллекции");
        commands.put("add_if_max {element}", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
        commands.put("remove_lower {element}", "удалить из коллекции все элементы, меньшие, чем заданный");
        commands.put("remove_any_by_color color", "удалить из коллекции один элемент, значение поля color которого эквивалентно заданному");
        commands.put("group_counting_by_character", "сгруппировать элементы коллекции по значению поля character, вывести количество элементов в каждой группе");
        commands.put("print_descending", "вывести элементы коллекции в порядке убывания");
        ArrayList<String> result = commands.entrySet().stream()
                .collect(ArrayList::new, (list, es) -> list.add(es.getKey() + ": " + es.getValue()), ArrayList::addAll);
        return new Response(ResultCode.DEFAULT, result);
    }

}
