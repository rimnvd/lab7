import utility.CollectionManager;
import utility.FileManager;
import utility.RequestProcess;
import utility.Server;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final String env = "FILE";
        int port;
        Server server;
        FileManager fileManager = new FileManager(env);
        CollectionManager collectionManager = new CollectionManager(fileManager);
        RequestProcess requestProcess = new RequestProcess(collectionManager);
        collectionManager.loadCollection();
        System.out.println("Введите порт");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                port = Integer.parseInt(scanner.nextLine().trim());
                server = new Server(port, requestProcess);
                break;
            } catch (NumberFormatException ex) {
                System.out.println("Неверный формат данных. Повторите ввод");
            } catch (NoSuchElementException ex) {
                collectionManager.saveToFile();
                System.exit(0);
            }
        }
        server.processRequest();

    }

}
