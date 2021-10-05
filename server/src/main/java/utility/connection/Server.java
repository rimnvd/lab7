package utility.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.CollectionManager;
import utility.database.DataBaseCollectionManager;
import utility.database.DataBaseUserManager;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;


public class Server {

    public static final Logger logger = LoggerFactory.getLogger(Server.class);
    private final ConnectionAccepter connectionAccepter;
    private final RequestReceiver requestReceiver;
    private final RequestHandler requestHandler;
    private final CollectionManager collectionManager;
    private final ForkJoinPool processThread = new ForkJoinPool();
    private final ExecutorService sendThread = Executors.newCachedThreadPool();
    private final DataBaseCollectionManager dbCollectionManager;

    public Server(int port, CollectionManager collectionManager, DataBaseUserManager dataBaseUserManager, DataBaseCollectionManager dbCollectionManager) throws IOException {
        this.connectionAccepter = new ConnectionAccepter(port);
        this.collectionManager = collectionManager;
        this.dbCollectionManager = dbCollectionManager;
        this.requestHandler = new RequestHandler(collectionManager, dataBaseUserManager, dbCollectionManager);
        this.requestReceiver = new RequestReceiver(connectionAccepter, requestHandler, processThread, sendThread);
        logger.info("Server has started on port {}\n", port);
    }

    public void run() {
        saveAndExit();
        while (true) {
            try {
                connectionAccepter.accept();
                logger.info("Client at {} has connected", connectionAccepter.getInetAddress());
                Thread thread = new Thread(requestReceiver);
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
