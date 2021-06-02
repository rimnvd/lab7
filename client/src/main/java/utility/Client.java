package utility;

import utility.exception.ServerUnavailableException;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.channels.UnresolvedAddressException;


public class Client {
    private SocketChannel channel;
    private final SocketAddress address;



    public Client(String ip, int port) throws UnresolvedAddressException{
        this.address = new InetSocketAddress(ip, port);
    }

    public void connect() throws ServerUnavailableException {
        long start = System.currentTimeMillis();
        while (true) {
            try {
                channel = SocketChannel.open(address);
                channel.configureBlocking(false);
                break;
            } catch (IOException | UnresolvedAddressException ex) {
                long end = System.currentTimeMillis();
                if (end - start > 5000) {
                    throw new ServerUnavailableException("\u001B[31m" + "Ошибка подкдючения к северу" + "\u001B[0m");
                }
            }
        }

    }

    public void send(Request request) throws IOException, ServerUnavailableException {
        if (channel == null) {
            connect();
        }
        ByteBuffer buffer = ByteBuffer.wrap(serialize(request));
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
    }

    public Response receive() throws IOException, ClassNotFoundException {
        Response response;
        ByteBuffer buffer = ByteBuffer.allocate(16384);
        while (buffer.position() < 8) {
            channel.read(buffer);
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        int numberOfBytes = objectInputStream.readInt();
        while (buffer.position() != numberOfBytes) {
            channel.read(buffer);
        }
        byteArrayInputStream = new ByteArrayInputStream(buffer.array());
        objectInputStream = new ObjectInputStream(byteArrayInputStream);
        objectInputStream.readInt();
        response = (Response) objectInputStream.readObject();
        return response;
    }

    public byte[] serialize(Request request) throws IOException{
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);
        return byteArrayOutputStream.toByteArray();
    }

}
