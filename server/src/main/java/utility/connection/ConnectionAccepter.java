package utility.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;



public class ConnectionAccepter {
    private final int port;
    private final ServerSocket serverSocket;
    private Socket socket;



    public ConnectionAccepter(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
    }

    public void accept() throws IOException {
        socket = serverSocket.accept();

    }

    public Socket getSocket() {
        return socket;
    }

    public InetAddress getInetAddress() {
        return socket.getInetAddress();
    }

}
