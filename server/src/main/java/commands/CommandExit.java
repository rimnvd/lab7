package commands;


import data.Dragon;
import utility.CollectionManager;
import utility.Response;

/**
 * This class is responsible for the completing the programme.
 */
public class CommandExit extends Command {
    private final CollectionManager collectionManager;

    public CommandExit(CollectionManager collectionManager) {
        super("exit");
        this.collectionManager = collectionManager;
    }


    @Override
    public Response execute(String enteredCommand, Dragon dragon) {
        collectionManager.saveToFile();
        return new Response();
    }
}
