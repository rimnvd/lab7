package commands;


import data.Dragon;
import utility.CollectionManager;
import utility.Response;

/**
 * This class is responsible for the removing all of the elements from the collection.
 */
public class CommandClear extends Command {
    private static final long serialVersionUID = 3L;
    private final CollectionManager collectionManager;

    public CommandClear(CollectionManager collectionManager) {
        super("clear");
        this.collectionManager = collectionManager;
    }


    @Override
    public Response execute(String enteredCommand, Dragon dragon) {
        collectionManager.clear();
        return new Response(CommandCode.CHANGE, "Коллекция успешно очищена");
    }
}
