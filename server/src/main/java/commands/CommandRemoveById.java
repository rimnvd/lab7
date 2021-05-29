package commands;


import data.Dragon;
import utility.CollectionManager;
import utility.Response;

/**
 * This class is responsible for the removing one element from the collection, id of which
 * coincides with the entered color.
 */
public class CommandRemoveById extends Command {
    private static final long serialVersionUID = 9L;
    private final CollectionManager collectionManager;

    public CommandRemoveById(CollectionManager collectionManager) {
        super("remove_by_id");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String enteredCommand, Dragon dragon) {
        if (collectionManager.isEmpty()) {
            return new Response("Невозможно выполнить данную команду, так как коллекция пуста\n");
        } else {
            if (collectionManager.removeById(Long.parseLong(argument(enteredCommand)))) {
                return new Response("Элемент успешно удален из коллекции\n");
            } else {
                return new Response("В коллекции нет элемента с таким значением id\n");
            }
        }
    }

}
