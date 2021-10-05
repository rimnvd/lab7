package utility.connection;

import commands.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.CollectionManager;
import utility.Request;
import utility.Response;
import utility.database.DataBaseCollectionManager;
import utility.database.DataBaseUserManager;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;

public class RequestHandler {
    public static final Logger logger = LoggerFactory.getLogger(Server.class);
    private final DataBaseUserManager dataBaseUserManager;
    HashMap<String, Command> commands = new HashMap<>();
    CollectionManager collectionManager;

    public RequestHandler(CollectionManager collectionManager, DataBaseUserManager dataBaseUserManager, DataBaseCollectionManager dbCollectionManager) {
        this.collectionManager = collectionManager;
        this.dataBaseUserManager = dataBaseUserManager;
        commands.put("add", new CommandAdd(collectionManager, dbCollectionManager));
        commands.put("add_if_max", new CommandAddIfMax(collectionManager, dbCollectionManager));
        commands.put("clear", new CommandClear(collectionManager, dbCollectionManager));
        commands.put("group_counting_by_character", new CommandGroupCountingByCharacter(collectionManager));
        commands.put("help", new CommandHelp());
        commands.put("info", new CommandInfo(collectionManager));
        commands.put("print_descending", new CommandPrintDescending(collectionManager));
        commands.put("remove_any_by_color", new CommandRemoveAnyByColor(collectionManager, dbCollectionManager));
        commands.put("remove_by_id", new CommandRemoveById(collectionManager, dbCollectionManager));
        commands.put("remove_last", new CommandRemoveLast(collectionManager, dbCollectionManager));
        commands.put("remove_lower", new CommandRemoveLower(collectionManager, dbCollectionManager));
        commands.put("show", new CommandShow(collectionManager));
        commands.put("update", new CommandUpdate(collectionManager, dbCollectionManager));
    }

    public Response process(Request request) {
        if (request.getPassword() == null) {
            try {
                if (dataBaseUserManager.isUserExist(request.getUsername())) {
                    return new Response(ResultCode.EXIST);
                } else {
                    return new Response(ResultCode.OK);
                }
            } catch (SQLException ex) {
                logger.warn("Problems with DB");
                return new Response(ResultCode.DBERROR);
            }
        } else if (request.getCommandName() == null && request.getIsRegister()) {
            try {
                if (dataBaseUserManager.signIn(request.getUsername(), request.getPassword())) {
                    logger.info("User {} has authorized", request.getUsername());
                    return new Response(ResultCode.OK);
                } else return new Response(ResultCode.WRONG);
            } catch (SQLException ex) {
                logger.warn("Problems with DB");
                return new Response(ResultCode.DBERROR);
            } catch (NoSuchAlgorithmException ex) {
                logger.warn("Hashing algorithm was not found");
                return new Response(ResultCode.DBERROR);
            }
        } else if (request.getCommandName() == null && !request.getIsRegister()) {
            try {
                dataBaseUserManager.register(request.getUsername(), request.getPassword());
                logger.info("User {} has registered", request.getUsername());
                return new Response(ResultCode.OK);
            } catch (SQLException ex) {
                logger.warn("Problems with DB");
                return new Response(ResultCode.DBERROR);
            } catch (NoSuchAlgorithmException ex) {
                logger.warn("Hashing algorithm was not found");
                return new Response(ResultCode.DBERROR);
            }
        }
        logger.info("Command {} has executed", request.getCommandName().getName());
        return commands.get(request.getCommandName().getName()).execute(request.getFullCommand(), request.getDragon(), request.getUsername());
    }


}

