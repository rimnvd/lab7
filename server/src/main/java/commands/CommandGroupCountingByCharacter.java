package commands;


import data.Dragon;
import utility.CollectionManager;
import utility.Response;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * This class is responsible for the grouping elements of the collection by character
 * and counting the number of the elements in each group.
 */
public class CommandGroupCountingByCharacter extends Command {
    private static final long serialVersionUID = 4L;
    private final CollectionManager collectionManager;

    public CommandGroupCountingByCharacter(CollectionManager collectionManager) {
        super("group_counting_by_character");
        this.collectionManager = collectionManager;
    }


    /**
     * Executes the command.
     *
     * @param enteredCommand the full name of the entered command
     */
    @Override
    public Response execute(String enteredCommand, Dragon dragon, String username) {
        if (collectionManager.isEmpty()) return new Response(ResultCode.DEFAULT, "Коллекция пуста");
        ArrayList<String> result = collectionManager.getCollection().stream()
                .collect(Collectors.groupingBy(Dragon::getCharacter, Collectors.counting()))
                .entrySet().stream().collect(ArrayList::new, (list, es) -> list.add(es.getKey() + ": " + es.getValue()), ArrayList::addAll);
        return new Response(ResultCode.DEFAULT, result);
    }


}
