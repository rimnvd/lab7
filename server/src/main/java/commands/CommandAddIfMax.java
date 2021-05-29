package commands;

import data.Dragon;
import utility.CollectionManager;
import utility.Response;


/**
 * This class is responsible for the adding an element to the collection
 * if this element is greater than the max element in the collection.
 */
public class CommandAddIfMax extends Command {
    private static final long serialVersionUID = 2L;
    private final CollectionManager collectionManager;

    public CommandAddIfMax(CollectionManager collectionManager) {
        super("add_if_max");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     *
     * @param enteredCommand the full name of the entered command
     */
    @Override
    public Response execute(String enteredCommand, Dragon dragon) {
        if (collectionManager.isEmpty()) {
            collectionManager.addToCollection(dragon);
            return new Response("Элемент успешно добавлен в коллекцию\n");
        } else if (dragon.compareTo(collectionManager.maxElement()) > 0) {
            collectionManager.addToCollection(dragon);
            return new Response("Элемент успешно добавлен в коллекцию\n");
        }
        return new Response();
    }

}
