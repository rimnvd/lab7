package commands;

import data.Dragon;
import utility.CollectionManager;
import utility.Response;
import utility.database.DataBaseCollectionManager;
import utility.database.DataBaseUserManager;
import utility.database.NoIdException;
import utility.database.NoPermissionException;

import java.sql.SQLException;


/**
 * This class is responsible for the removing all the elements from the collection that are lower than the entered element.
 */
public class CommandRemoveLower extends Command {
    private static final long serialVersionUID = 11L;
    private final CollectionManager collectionManager;
    private final DataBaseCollectionManager dbCollectionManager;

    public CommandRemoveLower(CollectionManager collectionManager, DataBaseCollectionManager dbCollectionManager) {
        super("remove_lower");
        this.collectionManager = collectionManager;
        this.dbCollectionManager = dbCollectionManager;
    }

    /**
     * Executes the command.
     *
     * @param enteredCommand The full name of the command.
     */
    @Override
    public Response execute(String enteredCommand, Dragon dragon, String username) {
        if (collectionManager.isEmpty()) {
            return new Response(ResultCode.ERROR, "Невозможно выполнить данную команду, так как коллекция пуста");
        } else {
            for (Dragon dragons : collectionManager.getCollection()) {
                if (dragons.compareTo(dragon) < 0) {
                    try {
                        dbCollectionManager.removeById(username, dragons.getId());
                    } catch (NoPermissionException | NoIdException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        return new Response(ResultCode.ERROR, "Невозможно выполнить данную команду");
                    }
                }
            }
            collectionManager.removeLower(dragon);
        }
        return new Response(ResultCode.DEFAULT);
    }
}

