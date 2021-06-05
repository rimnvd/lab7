import utility.CollectionManager;
import utility.ConsoleColor;
import utility.FileManager;
import utility.connection.Server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        final int port;
        final String env = "FILE";
        FileManager fileManager = new FileManager(env);
        CollectionManager collectionManager = new CollectionManager(fileManager);
        collectionManager.loadCollection();
        System.out.println();
        if (System.getenv().get("PORT") == null) {
            System.out.println(ConsoleColor.ANSI_RED.getColor() + "Environment variable PORT was not found" + ConsoleColor.ANSI_RESET.getColor());
        } else {
            try {
                port = Integer.parseInt(System.getenv("PORT"));
                if (port < 0 || port > 0xFFFF) {
                    System.out.println(ConsoleColor.ANSI_RED.getColor() + "Wrong data format of PORT variable" + ConsoleColor.ANSI_RESET.getColor());
                    return;
                }
                Server server = new Server(port, collectionManager);
                server.run();
            } catch (NumberFormatException ex) {
                System.out.println(ConsoleColor.ANSI_RED.getColor() + "Wrong data format of PORT variable" + ConsoleColor.ANSI_RESET.getColor());
            } catch (IOException ex) {
                System.out.println(ConsoleColor.ANSI_RED.getColor() + "IOException has happened. " + ex.getMessage() + ConsoleColor.ANSI_RESET.getColor());
            }
        }
    }

}
