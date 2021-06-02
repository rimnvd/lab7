import utility.*;
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
            System.out.println("\u001B[31m" + "Environment variable PORT was not found");
        } else {
            try {
                port = Integer.parseInt(System.getenv("PORT"));
                if (port < 0 || port > 0xFFFF) {
                    System.out.println("\u001B[31m" + "Wrong data format of PORT variable");
                    return;
                }
                Server server = new Server(port, collectionManager);
                server.run();
            } catch (NumberFormatException  ex) {
                System.out.println("\u001B[31m" + "Wrong data format of PORT variable");
            } catch (IOException ex) {
                System.out.println("\u001B[31m" + "IOException has happened. " + ex.getMessage());
            }
        }
    }

}
