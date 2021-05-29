package commands;

import data.Dragon;
import utility.Response;

/**
 * This class is responsible for the executing commands from the script.
 */
public class CommandExecuteScript extends Command {

    public CommandExecuteScript() {
        super("execute_script");
    }


    @Override
    public Response execute(String enteredCommand, Dragon dragon) {
        return new Response();
    }




}
