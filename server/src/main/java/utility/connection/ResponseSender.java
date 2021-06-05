package utility.connection;

import utility.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ResponseSender {

    public void send(Response response, Socket socket) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(response);
        objectOutputStream.writeInt(byteArrayOutputStream.size());
        byteArrayOutputStream.writeTo(socket.getOutputStream());
    }
}
