import utility.*;
import utility.connection.Server;
import utility.database.DataBaseCollectionManager;
import utility.database.DataBaseConnection;
import utility.database.DataBaseInitializer;
import utility.database.DataBaseUserManager;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        final int port;
        DataBaseConnection dataBaseConnection;
        DataBaseUserManager dataBaseUserManager;
        System.out.println();
        if (System.getenv().get("PORT") == null) {
            System.out.println(ConsoleColor.ANSI_RED.getColor() + "Environment variable PORT was not found" + ConsoleColor.ANSI_RESET.getColor());
        } else {
            try {
                port = Integer.parseInt(System.getenv("PORT"));
                if (port < 0 || port > 0xFFFF) {
                    System.out.println(ConsoleColor.ANSI_RED.getColor() + "Wrong data format of PORT variable" + ConsoleColor.ANSI_RESET.getColor());
                }
                dataBaseConnection = new DataBaseConnection();
                DataBaseInitializer dataBaseInitializer = new DataBaseInitializer(dataBaseConnection);
                dataBaseUserManager = new DataBaseUserManager(dataBaseConnection);
                CollectionManager collectionManager = new CollectionManager();
                DataBaseCollectionManager dataBaseCollectionManager = new DataBaseCollectionManager(dataBaseConnection);
                dataBaseInitializer.initializeTables();
                dataBaseCollectionManager.loadCollectionFromDB(collectionManager.getCollection());
                Server server = new Server(port, collectionManager, dataBaseUserManager, dataBaseCollectionManager);
                server.run();
            } catch (NumberFormatException ex) {
                System.out.println(ConsoleColor.ANSI_RED.getColor() + "Wrong data format of PORT variable" + ConsoleColor.ANSI_RESET.getColor());
            } catch (IOException ex) {
                System.out.println(ConsoleColor.ANSI_RED.getColor() + "IOException has happened. " + ex.getMessage() + ConsoleColor.ANSI_RESET.getColor());
            } catch (ExitException ex) {
                System.out.println("the end");
            } catch (SQLException ex) {
                System.out.println("Cannot connect to database");
                ex.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("Driver for postgreSQL was not found");
            }
        }
    }

}
