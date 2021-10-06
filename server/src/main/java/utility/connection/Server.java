package utility.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.CollectionManager;
import utility.database.DataBaseCollectionManager;
import utility.database.DataBaseUserManager;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;


public class Server {

    public static final Logger logger = LoggerFactory.getLogger(Server.class);
    private final ConnectionAccepter connectionAccepter;
    private final RequestHandler requestHandler;
    private final ForkJoinPool processThread = new ForkJoinPool(Runtime.getRuntime().availableProcessors() / 3);
    private final ExecutorService sendThread = Executors.newCachedThreadPool();



    public Server(int port, CollectionManager collectionManager, DataBaseUserManager dataBaseUserManager, DataBaseCollectionManager dbCollectionManager) throws IOException {
        this.connectionAccepter = new ConnectionAccepter(port);
        this.requestHandler = new RequestHandler(collectionManager, dataBaseUserManager, dbCollectionManager);
        logger.info("Server has started on port {}\n", port);
    }

    public void run() {
        saveAndExit();
        while (true) {
            try {
                connectionAccepter.accept();
                Socket socket = connectionAccepter.getSocket();
                logger.info("Client at {} has connected", connectionAccepter.getInetAddress());
                Thread thread = new Thread(new RequestReceiver(requestHandler, processThread, sendThread, socket));
                thread.start();
            } catch (IOException e) {
                logger.warn("IOException has happened. {}", e.getMessage());
            }
        }
    }

    private void saveAndExit() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("The server application has successfully finished");
            System.out.println();
        }));
    }
}
