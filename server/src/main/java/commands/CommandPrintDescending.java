package commands;


import data.Dragon;
import utility.CollectionManager;
import utility.Response;

import java.util.ArrayList;
import java.util.Comparator;

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
            return new Response(CommandCode.DEFAULT, "Коллекция пуста");
        }
        ArrayList<String> result = collectionManager.getCollection().stream()
                .sorted(Comparator.reverseOrder())
                .collect(ArrayList::new, (list, drag) -> list.add(drag.toString()), ArrayList::addAll);
        return new Response(CommandCode.DEFAULT, result);
    }
}
