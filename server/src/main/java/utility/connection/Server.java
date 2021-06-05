package utility.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.CollectionManager;
import utility.Request;
import utility.Response;

import java.io.IOException;
import java.net.Socket;


public class Server {

    private final Logger logger = LoggerFactory.getLogger(Server.class);
    private final ConnectionAccepter connectionAccepter;
    private final RequestReceiver requestReceiver;
    private final ProcessingRequest processingRequest;
    private final ResponseSender responseSender;
    private final CollectionManager collectionManager;

    public Server(int port, CollectionManager collectionManager) throws IOException {
        this.connectionAccepter = new ConnectionAccepter(port);
        this.requestReceiver = new RequestReceiver();
        this.collectionManager = collectionManager;
        this.processingRequest = new ProcessingRequest(collectionManager);
        this.responseSender = new ResponseSender();
        logger.info("Server has started on port {}\n", port);
    }

    public void run() {
        saveAndExit();
        while (true) {
            Response response;
            Request request;
            try {
                connectionAccepter.accept();
                Socket socket = connectionAccepter.getSocket();
                logger.info("Client at {} has connected", connectionAccepter.getInetAddress());
                request = requestReceiver.getRequest(socket);
                logger.info("Request has received");
                response = processingRequest.process(request);
                logger.info("Command {} has executed", request.getCommandName().getName());
                responseSender.send(response, socket);
                logger.info("Response has been sent\n");
            } catch (IOException e) {
                logger.warn("IOException has happened. {}", e.getMessage());
            } catch (ClassNotFoundException ex) {
                logger.warn("Class not found");
            }
        }
    }

    private void saveAndExit() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            collectionManager.saveToFile();
            logger.info("The server application has successfully finished");
            System.out.println();
        }));
    }
}
