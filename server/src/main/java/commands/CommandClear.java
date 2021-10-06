package commands;


import data.Dragon;
import utility.CollectionManager;
import utility.Response;
import utility.database.DataBaseCollectionManager;

/**
 * This class is responsible for the removing all of the elements from the collection.
 */
public class CommandClear extends Command {
    private static final long serialVersionUID = 3L;
    private final CollectionManager collectionManager;
    private final DataBaseCollectionManager dbCollectionManager;

    public CommandClear(CollectionManager collectionManager, DataBaseCollectionManager dbCollectionManager) {
        super("clear");
        this.collectionManager = collectionManager;
        this.dbCollectionManager = dbCollectionManager;
    }


    @Override
    public Response execute(String enteredCommand, Dragon dragon, String username) {
        dbCollectionManager.clear(username);
        collectionManager.clear(username);
        dbCollectionManager.restartSequence(collectionManager);
        return new Response(ResultCode.CHANGE, "Коллекция успешно очищена");
    }
}
