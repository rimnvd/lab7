package utility.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

public class RequestReceiver extends Thread {
    private Request request;
    private final ConnectionAccepter connectionAccepter;
    public static final Logger logger = LoggerFactory.getLogger(Server.class);
    private ProcessingRequest processingRequest;

    public RequestReceiver(ConnectionAccepter connectionAccepter, RequestHandler requestHandler, ForkJoinPool processThreads,
                           ExecutorService sendThreads) {
        this.connectionAccepter = connectionAccepter;
        this.processingRequest= new ProcessingRequest(requestHandler, processThreads, sendThreads);
    }

    @Override
    public void run() {
        try {
            Socket socket = connectionAccepter.getSocket();
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            request = (Request) objectInputStream.readObject();
            logger.info("Request has received");
            processingRequest.process(request, socket);
        } catch (IOException ex) {
            logger.warn("IOException has happened. {}", ex.getMessage());
        } catch (ClassNotFoundException ex) {
            logger.warn("Class not found");
        }
    }
}
