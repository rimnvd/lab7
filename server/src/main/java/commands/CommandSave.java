package commands;


import data.Dragon;
import utility.CollectionManager;
import utility.Response;

/**
 * This class is responsible for saving the collection to the file.
 */
public class CommandSave extends Command {
    private final CollectionManager collectionManager;

    public CommandSave(CollectionManager collectionManager) {
        super("save");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     *
     */
    @Override
    public Response execute(String enteredCommand, Dragon dragon) {
        collectionManager.saveToFile();
        return new Response();
    }
}
