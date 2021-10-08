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
 * This class is responsible for the adding an element to the collection.
 */
public class CommandAdd extends Command {
    private static final long serialVersionUID = 1L;
    private final CollectionManager collectionManager;
    private final DataBaseCollectionManager dbCollectionManager;
    public static final Logger logger = LoggerFactory.getLogger(Server.class);

    public CommandAdd(CollectionManager collectionManager, DataBaseCollectionManager dbCollectionManager) {
        super("add");
        this.collectionManager = collectionManager;
        this.dbCollectionManager = dbCollectionManager;


    }

    @Override
    public Response execute(String enteredCommand, Dragon dragon, String username) {
        try {
            Long newId = dbCollectionManager.insertDragon(dragon, username);
            collectionManager.addToCollection(dragon, newId);
            return new Response(ResultCode.CHANGE, "Элемент успешно добавлен в коллекцию");
        } catch (SQLException ex) {
            logger.warn("Problems with database");
            return new Response(ResultCode.DBERROR, "Невозможно выполнить данную команду, " +
                    "так как произошла ошибка при работе с базой данных");
        }

    }

}


