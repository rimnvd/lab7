package utility.connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

public class RequestReceiver implements Runnable {
    public static final Logger logger = LoggerFactory.getLogger(Server.class);
    private final ProcessingRequest processingRequest;
    private final Socket socket;

    public RequestReceiver(RequestHandler requestHandler, ForkJoinPool processThreads,
                           ExecutorService sendThreads, Socket socket) {
        this.processingRequest= new ProcessingRequest(requestHandler, processThreads, sendThreads);
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Request request = (Request) objectInputStream.readObject();
            logger.info("Request has received");
            processingRequest.process(request, socket);
        } catch (IOException ex) {
            logger.warn("IOException has happened. {}", ex.getMessage());
        } catch (ClassNotFoundException ex) {
            logger.warn("Class not found");
        }
    }
}
