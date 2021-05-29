package utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private final int port;
    private ServerSocket serverSocket;
    private Socket socket;
    private Request request;
    private final RequestProcess requestProcess;
    //private static final Logger logger = LoggerFactory.getLogger(utility.Server.class);



    public Server(int port, RequestProcess requestProcess) {
        this.port = port;
        this.requestProcess = requestProcess;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
        }
    }


    public void processRequest() {
        while (true) {
            Response response;
            try {
                request = getRequest();
                response = requestProcess.processing(request);
                send(response);
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }

    }

    public Request getRequest() throws IOException, ClassNotFoundException {
        socket = serverSocket.accept();
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        return (Request) objectInputStream.readObject();
    }

    public byte[] serialize (Response response) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(response);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return buffer;
    }

    public void send(Response response) throws IOException {
        byte[] sendBuffer = serialize(response);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.write(sendBuffer);
    }



}
