package commands;

import data.Color;
import data.Dragon;
import utility.CollectionManager;
import utility.Response;
import utility.database.DataBaseCollectionManager;

/**
 * This class is responsible for the removing one element from the the collection, color of which
 * coincides with the entered color.
 */
public class CommandRemoveAnyByColor extends Command {
    private static final long serialVersionUID = 8L;
    private final CollectionManager collectionManager;
    private final DataBaseCollectionManager dbCollectionManager;

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
        } else if (dbCollectionManager.removeByColor(username, Color.valueOf(argument(enteredCommand).toUpperCase()))) {
            if (collectionManager.removeByColor(Color.valueOf(argument(enteredCommand).toUpperCase()), username))
                return new Response(ResultCode.CHANGE, "Элемент успешно удален из коллекции");
            else
                return new Response(ResultCode.ERROR, "Невозможно выполнить данную команду, так как в коллекции нет элемента с таким полем Color");
        }
        return new Response(ResultCode.ERROR, "Невозможно выполнить данную команду");
    }
}
