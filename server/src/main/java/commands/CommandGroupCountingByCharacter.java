package commands;


import data.Dragon;
import utility.CollectionManager;
import utility.Response;

/**
 * This class is responsible for the grouping elements of the collection by character
 * and counting the number of the elements in each group.
 */
public class CommandGroupCountingByCharacter extends Command {
    private static final long serialVersionUID = 4L;
    private final CollectionManager collectionManager;

    public CommandGroupCountingByCharacter(CollectionManager collectionManager) {
        super("group_counting_by_character");
        this.collectionManager = collectionManager;
    }


    /**
     * Executes the command.
     *
     * @param enteredCommand the full name of the entered command

     */
    @Override
    public Response execute(String enteredCommand, Dragon dragon) {
        int good;
        int cunning;
        int chaotic_evil;
        good = collectionManager.countCharacter("GOOD");
        cunning = collectionManager.countCharacter("CUNNING");
        chaotic_evil = collectionManager.countCharacter("CHAOTIC_EVIL");
        return new Response("CUNNING: " + cunning + "\n" + "GOOD: " + good + "\n" + "CHAOTIC_EVIL: " + chaotic_evil + "\n");
    }


}
