package commands;

import data.Dragon;
import utility.CollectionManager;
import utility.Response;
import utility.database.DataBaseCollectionManager;


/**
 * This class is responsible for the adding an element to the collection.
 */
public class CommandAdd extends Command {
    private static final long serialVersionUID = 1L;
    private final CollectionManager collectionManager;
    private final DataBaseCollectionManager dbCollectionManager;

    public CommandAdd(CollectionManager collectionManager, DataBaseCollectionManager dbCollectionManager) {
        super("add");
        this.collectionManager = collectionManager;
        this.dbCollectionManager = dbCollectionManager;


    }

    @Override
    public Response execute(String enteredCommand, Dragon dragon, String username) {
        Long newId = dbCollectionManager.insertDragon(dragon, username);
        if (newId != null) {
            collectionManager.addToCollection(dragon, newId);
                return new Response(ResultCode.CHANGE, "Элемент успешно добавлен в коллекцию");
        }
        return new Response(ResultCode.ERROR, "Элемент не может быть добавлен в коллекцию");
    }

}


