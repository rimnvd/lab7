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
 * This class is responsible for the updating the element, id of which coincides with the entered id.
 */
public class CommandUpdate extends Command {
    private static final long serialVersionUID = 13L;
    private final CollectionManager collectionManager;
    private final DataBaseCollectionManager dbCollectionManager;
    public static final Logger logger = LoggerFactory.getLogger(Server.class);

    public CommandUpdate(CollectionManager collectionManager, DataBaseCollectionManager dbCollectionManager) {
        super("update");
        this.collectionManager = collectionManager;
        this.dbCollectionManager = dbCollectionManager;
    }

    /**
     * Executes the command.
     *
     * @param enteredCommand the full name of the entered command
     */
    public Response execute(String enteredCommand, Dragon dragon, String username) {
        if (collectionManager.isEmpty()) {
            return new Response(ResultCode.ERROR, "Невозможно выполнить данную команду, так как коллекция пуста");
        } else {
            Long id = Long.parseLong(argument(enteredCommand));
            try {
                dbCollectionManager.updateById(username, id, dragon);
            } catch (NoPermissionException e) {
                return new Response(ResultCode.ERROR, "Невозможно выполнить данную команду, " +
                        "так как у вас нет доступа к элементу с id = " + id);
            } catch (NoIdException e) {
                return new Response(ResultCode.ERROR, "Невозможно выполнить данную команду, " +
                        "так как в коллекции нет элемента с таким значением id");
            } catch (SQLException e) {
                logger.warn("Problems with database");
                return new Response(ResultCode.DBERROR, "Невозможно выполнить данную команду," +
                        "так как произошла ошибка при работе с базой данных");
            }
            collectionManager.updateById(Long.parseLong(argument(enteredCommand)), dragon);
            return new Response(ResultCode.CHANGE, "Элемент с id " + argument(enteredCommand) + " успешно обновлен");
        }
    }
}


