package commands;


import data.Dragon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.CollectionManager;
import utility.Response;
import utility.connection.Server;
import utility.database.DataBaseCollectionManager;
import utility.database.NoPermissionException;

import java.sql.SQLException;

/**
 * This class is responsible for the removing the last element from the collection.
 */
public class CommandRemoveLast extends Command {
    private static final long serialVersionUID = 10L;
    private final CollectionManager collectionManager;
    private final DataBaseCollectionManager dbCollectionManager;
    public static final Logger logger = LoggerFactory.getLogger(Server.class);

    public CommandRemoveLast(CollectionManager collectionManager, DataBaseCollectionManager dbCollectionManager) {
        super("remove_last");
        this.collectionManager = collectionManager;
        this.dbCollectionManager = dbCollectionManager;

    }


    /**
     * Executes the command.
     *
     * @param enteredCommand the full name of the entered command
     */
    @Override
    public Response execute(String enteredCommand, Dragon dragon, String username) {
        if (collectionManager.isEmpty()) {
            return new Response(ResultCode.ERROR, "Невозможно выполнить данную команду, так как коллекция пуста");
        } else {
            Long id = collectionManager.getCollection().get(collectionManager.getCollection().size() - 1).getId();
            try {
                dbCollectionManager.removeLast(username, collectionManager);
                collectionManager.removeLast(username);
                dbCollectionManager.restartSequence(collectionManager.getLastId() + 1);
                return new Response(ResultCode.CHANGE, "Элемент успешно удален из коллекции");
            } catch (NoPermissionException e) {
                return new Response(ResultCode.ERROR, "Невозможно выполнить данную команду, так как у вас нет доступа к элементу с id = " + id);
            } catch (SQLException e) {
                logger.warn("Problems with database");
                return new Response(ResultCode.DBERROR, "Невозможно выполнить данную команду," +
                        "так как произошла ошибка при работе с базой данных");
            }
        }
    }
}
