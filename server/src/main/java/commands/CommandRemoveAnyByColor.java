package commands;

import data.Color;
import data.Dragon;
import utility.CollectionManager;
import utility.Response;

/**
 * This class is responsible for the removing one element from the the collection, color of which
 * coincides with the entered color.
 */
public class CommandRemoveAnyByColor extends Command {
    private static final long serialVersionUID = 8L;
    private final CollectionManager collectionManager;

    public CommandRemoveAnyByColor(CollectionManager collectionManager) {
        super("remove_any_by_color");
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
            return new Response(CommandCode.ERROR, "Невозможно выполнить данную команду, так как коллекция пуста");
        } else {
            if (collectionManager.removeByColor(Color.valueOf(argument(enteredCommand).toUpperCase()))) {
                return new Response(CommandCode.CHANGE, "Элемент успешно удален из коллекции");
            } else
                return new Response(CommandCode.ERROR, "Невозможно выполнить данную команду, так как в коллекции нет элемента с таким полем Color");
        }
    }


}
