package commands;

import data.Dragon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.CollectionManager;
import utility.Response;
import utility.connection.Server;
import utility.database.DataBaseCollectionManager;
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
    public static final Logger logger = LoggerFactory.getLogger(Server.class);

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
                        if (e.getMessage() != null) System.out.println(e.getMessage());
                    } catch (SQLException e) {
                        logger.warn("Problems with database");
                        return new Response(ResultCode.DBERROR, "Невозможно выполнить данную команду," +
                                "так как произошла ошибка при работе с базой данных");
                    }
                }
            }
            collectionManager.removeLower(dragon, username);
            dbCollectionManager.restartSequence(collectionManager.getLastId() + 1);
        }
        return new Response(ResultCode.DEFAULT);
    }
}

