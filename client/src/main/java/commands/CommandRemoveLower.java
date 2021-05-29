package commands;

import data.Dragon;
import utility.ElementCreation;


/**
 * This class is responsible for the removing all the elements from the collection that are lower than the entered element.
 */
public class CommandRemoveLower extends Command {
    private static final long serialVersionUID = 11L;
    private final ElementCreation elementCreation;

    public CommandRemoveLower(ElementCreation elementCreation) {
        super("remove_lower");
        this.elementCreation = elementCreation;
    }

    /**
     * Executes the command.
     *
     * @param EnteredCommand The full name of the command.
     * @return
     */
    @Override
    public boolean execute(String EnteredCommand) {
        if (!checkCommand(EnteredCommand)) {
            System.out.println("Команда не найдена. Введите \"help\" для справки");
            return false;
        } else {
            Dragon dragon = elementCreation.createElement();
            return true;
        }
    }
}

