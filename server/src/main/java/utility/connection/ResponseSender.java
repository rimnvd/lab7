package utility.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ResponseSender implements Runnable {
    private final Response response;
    private final Socket socket;
    public static final Logger logger = LoggerFactory.getLogger(Server.class);

    public ResponseSender(Response response, Socket socket) {
        this.response = response;
        this.socket = socket;
    }

    public void run() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(response);
            socket.getOutputStream().write(ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array());
            byteArrayOutputStream.writeTo(socket.getOutputStream());
            logger.info("Response has been sent\n");
        } catch (IOException ex) {
            logger.warn("IOException has happened. {}", ex.getMessage());
        }
    }
}
