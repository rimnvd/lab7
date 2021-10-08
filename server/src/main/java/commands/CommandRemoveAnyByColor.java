package commands;

import data.Color;
import data.Dragon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.CollectionManager;
import utility.Response;
import utility.connection.Server;
import utility.database.DataBaseCollectionManager;
import utility.database.NoColorException;
import utility.database.NoPermissionException;

import java.sql.SQLException;

/**
 * This class is responsible for the removing one element from the the collection, color of which
 * coincides with the entered color.
 */
public class CommandRemoveAnyByColor extends Command {
    private static final long serialVersionUID = 8L;
    private final CollectionManager collectionManager;
    private final DataBaseCollectionManager dbCollectionManager;
    public static final Logger logger = LoggerFactory.getLogger(Server.class);

    public CommandRemoveAnyByColor(CollectionManager collectionManager, DataBaseCollectionManager dbCollectionManager) {
        super("remove_any_by_color");
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
            try {
                dbCollectionManager.removeByColor(username, Color.valueOf(argument(enteredCommand).toUpperCase()));
                collectionManager.removeByColor(Color.valueOf(argument(enteredCommand).toUpperCase()), username);
                dbCollectionManager.restartSequence(collectionManager.getLastId() + 1);
                return new Response(ResultCode.CHANGE, "Элемент успешно удален из коллекции");
            } catch (SQLException e) {
                logger.warn("Problems with database");
                return new Response(ResultCode.DBERROR, "Невозможно выполнить данную команду, " +
                        "так как произошла ошибка при работе с базой данных");
            } catch (NoColorException e) {
                return new Response(ResultCode.ERROR, "Невозможно выполнить данную команду, " +
                        "так как в коллекции нет элемента с таким полем Color");
            } catch (NoPermissionException e) {
               return new Response(ResultCode.ERROR, "Невозможно выполнить данную команду, " +
                       "так как у вас нет доступа ни к одному элементу с таким полем Color");
            }
        }
    }
}
