package commands;


import data.Dragon;
import utility.CollectionManager;
import utility.Response;

/**
 * This class is responsible for giving information about the elements in the collection.
 */
public class CommandShow extends Command {
    private static final long serialVersionUID = 12L;
    private final CollectionManager collectionManager;

    public CommandShow(CollectionManager collectionManager) {
        super("show");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     *
     * @param enteredCommand the full name of the entered command
     */
    public Response execute(String enteredCommand, Dragon dragon) {
        if (collectionManager.isEmpty()) {
            return new Response("Коллекция пуста\n");
        } else {
            StringBuilder message = new StringBuilder();
            for (int i = 0; i < collectionManager.collectionSize(); i++) {
                message.append(collectionManager.getCollection().get(i).toString());
                if (i != collectionManager.collectionSize() - 1) {
                    message.append("\n");
                }
            }
            return new Response(message.toString());
        }
    }
}
