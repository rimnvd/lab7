package commands;


import data.Dragon;
import utility.CollectionManager;
import utility.Response;

/**
 * This class is responsible for the removing the last element from the collection.
 */
public class CommandRemoveLast extends Command {
    private static final long serialVersionUID = 10L;
    private final CollectionManager collectionManager;

    public CommandRemoveLast(CollectionManager collectionManager) {
        super("remove_last");
        this.collectionManager = collectionManager;
    }


    /**
     * Executes the command.
     *
     * @param enteredCommand the full name of the entered command
     */
    @Override
    public Response execute(String enteredCommand, Dragon dragon) {
        if (collectionManager.isEmpty()) {
            return new Response("\u001B[31m" + "Невозможно выполнить данную команду, так как коллекция пуста" + "\u001B[0m");
        } else {
            collectionManager.removeLast();
            return new Response("\u001B[32m" + "Элемент успешно удален из коллекции" + "\u001B[0m");
        }
    }
}
