package commands;


import data.Dragon;
import utility.CollectionManager;
import utility.Response;

import java.util.Vector;

/**
 * This class is responsible for printing the collection in descending order.
 */
public class CommandPrintDescending extends Command {
    private static final long serialVersionUID = 7L;
    private final CollectionManager collectionManager;

    public CommandPrintDescending(CollectionManager collectionManager) {
        super("print_descending");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     *
     * @param enteredCommand the name of the specified command

     */
    @Override
    public Response execute(String enteredCommand, Dragon dragon) {
        if (collectionManager.isEmpty()) {
            return new Response("Коллекция пуста");
        } else {
            Vector<Dragon> vector = new Vector<>(collectionManager.getCollection());
            collectionManager.reverseSort(vector);
            StringBuilder message = new StringBuilder();
            for (Dragon dragons : vector) {
                message.append("Dragon ").append(dragons.getName()).append("\n");
            }
            message.deleteCharAt(message.length() - 1);
            return new Response(message.toString());
        }
    }
}
