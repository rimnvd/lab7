package commands;

import data.Dragon;
import utility.CollectionManager;
import utility.Response;


/**
 * This class is responsible for the removing all the elements from the collection that are lower than the entered element.
 */
public class CommandRemoveLower extends Command {
    private static final long serialVersionUID = 11L;
    private final CollectionManager collectionManager;

    public CommandRemoveLower(CollectionManager collectionManager) {
        super("remove_lower");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     *
     * @param enteredCommand The full name of the command.
     */
    @Override
    public Response execute(String enteredCommand, Dragon dragon) {
        if (collectionManager.isEmpty()) {
            return new Response("\u001B[31m" + "Невозможно выполнить данную команду, так как коллекция пуста" + "\u001B[0m");
        } else {
            collectionManager.removeLower(dragon);
        }
        return new Response();
    }
}

