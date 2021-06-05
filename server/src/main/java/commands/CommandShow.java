package commands;


import data.Dragon;
import utility.CollectionManager;
import utility.Response;

import java.util.ArrayList;

/**
 * This class is responsible for giving information about the elements in the collection.
 */
public class CommandShow extends Command {
    private static final long serialVersionUID = 12L;
    private final CollectionManager collectionManager;

    public CommandShow(CollectionManager collectionManager) {
        super("show");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     *
     * @param enteredCommand the full name of the entered command
     */
    public Response execute(String enteredCommand, Dragon dragon) {
        if (collectionManager.isEmpty()) {
            return new Response(CommandCode.DEFAULT, "Коллекция пуста");
        }
        ArrayList<String> result = collectionManager.getCollection().stream()
                .sorted().collect(ArrayList::new, (list, drag) -> list.add(drag.toString()), ArrayList::addAll);
        return new Response(CommandCode.DEFAULT, result);
    }
}
