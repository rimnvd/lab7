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
 * This class is responsible for the removing one element from the collection, id of which
 * coincides with the entered color.
 */
public class CommandRemoveById extends Command {
    private static final long serialVersionUID = 9L;
    private final CollectionManager collectionManager;
    private final DataBaseCollectionManager dbCollectionManager;
    public static final Logger logger = LoggerFactory.getLogger(Server.class);

    public CommandRemoveById(CollectionManager collectionManager, DataBaseCollectionManager dbCollectionManager) {
        super("remove_by_id");
        this.collectionManager = collectionManager;
        this.dbCollectionManager = dbCollectionManager;
    }

    @Override
    public Response execute(String enteredCommand, Dragon dragon, String username) {
        if (collectionManager.isEmpty()) {
            return new Response(ResultCode.ERROR, "Невозможно выполнить данную команду, так как коллекция пуста");
        } else {
            Long id = Long.parseLong(argument(enteredCommand));
            try {
                dbCollectionManager.removeById(username, id);
                collectionManager.removeById(Long.parseLong(argument(enteredCommand)), username);
                dbCollectionManager.restartSequence(collectionManager.getLastId() + 1);
                return new Response(ResultCode.CHANGE, "Элемент успешно удален из коллекции");
            } catch (NoPermissionException e) {
                return new Response(ResultCode.ERROR, "Невозможно выполнить данную команду, так как у вас нет доступа к элементу с id = " + id);
            } catch (SQLException e) {
                logger.warn("Problems with database");
                return new Response(ResultCode.DBERROR, "Невозможно выполнить данную команду," +
                        "так как произошла ошибка при работе с базой данных");
            } catch (NoIdException e) {
                return new Response(ResultCode.ERROR, "Невозможно выполнить данную команду, так как в коллекции нет элемента с таким значением id");
            }
        }
    }

}
