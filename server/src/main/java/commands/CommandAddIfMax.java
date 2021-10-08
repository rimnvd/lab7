package commands;

import data.Dragon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.CollectionManager;
import utility.Response;
import utility.connection.Server;
import utility.database.DataBaseCollectionManager;

import java.sql.SQLException;


/**
 * This class is responsible for the adding an element to the collection
 * if this element is greater than the max element in the collection.
 */
public class CommandAddIfMax extends Command {
    private static final long serialVersionUID = 2L;
    private final CollectionManager collectionManager;
    private final DataBaseCollectionManager dbCollectionManager;
    public static final Logger logger = LoggerFactory.getLogger(Server.class);

    public CommandAddIfMax(CollectionManager collectionManager, DataBaseCollectionManager dbCollectionManager) {
        super("add_if_max");
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
        try {
            if (collectionManager.isEmpty()) {
                Long newId = dbCollectionManager.insertDragon(dragon, username);
                    collectionManager.addToCollection(dragon, newId);
                    return new Response(ResultCode.CHANGE, "Элемент успешно добавлен в коллекцию");
            } else if (dragon.compareTo(collectionManager.maxElement()) > 0) {
                Long newId = dbCollectionManager.insertDragon(dragon, username);
                    collectionManager.addToCollection(dragon, newId);
                    return new Response(ResultCode.CHANGE, "Элемент успешно добавлен в коллекцию");
            }
            return new Response(ResultCode.DEFAULT);
        } catch (SQLException ex) {
            logger.warn("Problems with database");
            return new Response(ResultCode.DBERROR, "Невозможно выполнить данную команду, " +
                    "так как произошла ошибка при работе с базой данных");
        }

    }

}
