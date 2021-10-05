package commands;


import data.Dragon;
import utility.CollectionManager;
import utility.Response;
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
                return new Response(ResultCode.CHANGE, "Элемент успешно удален из коллекции");
            } catch (NoPermissionException e) {
                return new Response(ResultCode.ERROR, "Невозможно выполнить данную команду, так как у вас нет доступа к элементу с id = " + id);
            } catch (SQLException e) {
                return new Response(ResultCode.ERROR, "Невозможно выполнить данную команду");
            } catch (NoIdException e) {
                return new Response(ResultCode.ERROR, "Невозможно выполнить данную команду, так как в коллекции нет элемента с таким значением id");
            }
        }
    }

}
