package commands;


import data.Dragon;
import utility.CollectionManager;
import utility.Response;

/**
 * This class is responsible for giving information about the collection.
 */
public class CommandInfo extends Command {
    private static final long serialVersionUID = 6L;
    CollectionManager collectionManager;


    public CommandInfo(CollectionManager collectionManager) {
        super("info");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     *
     * @param enteredCommand the full name of the entered command
     * @return
     */
    @Override
    public Response execute(String enteredCommand, Dragon dragon) {
        return new Response("Тип коллекции: " + collectionManager.collectionType() + "Дата инициализации коллекции: " + collectionManager.getDate() + "Количество элементов коллекции: " + collectionManager.collectionSize());
    }
}

