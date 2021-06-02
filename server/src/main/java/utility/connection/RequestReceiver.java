package utility.connection;

import utility.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class RequestReceiver {

    public Request getRequest(Socket socket) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        return (Request) objectInputStream.readObject();
    }
}
