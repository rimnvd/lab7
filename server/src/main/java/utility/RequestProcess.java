package utility;

import commands.*;

import java.util.HashMap;

public class RequestProcess {
    HashMap<String, Command> commands = new HashMap<>();
    CollectionManager collectionManager;

    public RequestProcess(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        commands.put("add", new CommandAdd(collectionManager));
        commands.put("add_if_max", new CommandAddIfMax(collectionManager));
        commands.put("clear", new CommandClear(collectionManager));
        commands.put("execute_script", new CommandExecuteScript());
        commands.put("exit", new CommandExit(collectionManager));
        commands.put("group_counting_by_character", new CommandGroupCountingByCharacter(collectionManager));
        commands.put("help", new CommandHelp());
        commands.put("info", new CommandInfo(collectionManager));
        commands.put("print_descending", new CommandPrintDescending(collectionManager));
        commands.put("remove_any_by_color", new CommandRemoveAnyByColor(collectionManager));
        commands.put("remove_by_id", new CommandRemoveById(collectionManager));
        commands.put("remove_last", new CommandRemoveLast(collectionManager));
        commands.put("remove_lower", new CommandRemoveLower(collectionManager));
        commands.put("save", new CommandSave(collectionManager));
        commands.put("show", new CommandShow(collectionManager));
        commands.put("update", new CommandUpdate(collectionManager));
    }

    public Response processing(Request request) {
        return commands.get(request.getCommandName().commandName(request.getFullCommand())).execute(request.getFullCommand(), request.getDragon());
    }

}

