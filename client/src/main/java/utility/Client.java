package utility;

import commands.ResultCode;
import exceptions.ServerUnavailableException;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.channels.UnresolvedAddressException;


public class Client {
    private final SocketAddress address;
    private SocketChannel channel;


    public Client(String ip, int port) throws UnresolvedAddressException {
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
                    throw new ServerUnavailableException();
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
        ByteBuffer buffer = ByteBuffer.allocate(4);
        do {
            channel.read(buffer);
        } while (buffer.hasRemaining());
        buffer.flip();
        buffer = ByteBuffer.allocate(buffer.getInt());

        while (buffer.hasRemaining()) {
            channel.read(buffer);
        }

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        response = (Response) objectInputStream.readObject();
        return response;
    }

    public byte[] serialize(Request request) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);
        return byteArrayOutputStream.toByteArray();
    }

    public void getResponse(Response response) {
        if (response.getResultCode() == ResultCode.ERROR) {
            System.out.println(ConsoleColor.ANSI_RED.getColor() + response.getMessage() + ConsoleColor.ANSI_RESET.getColor());
        } else if (response.getResultCode() == ResultCode.DEFAULT) {
            if (response.getResult() != null) {
                response.getResult().forEach(System.out::println);
            } else if (!response.getMessage().equals("")) System.out.println(response.getMessage());
        } else if (response.getResultCode() == ResultCode.NOTFOUND) System.out.println(ConsoleColor.ANSI_GREEN.getColor() + response.getMessage() + ConsoleColor.ANSI_RESET.getColor());
        else {
            System.out.println(ConsoleColor.ANSI_GREEN.getColor() + response.getMessage() + ConsoleColor.ANSI_RESET.getColor());
        }
    }
}
