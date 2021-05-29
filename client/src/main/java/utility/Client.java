package utility;

import utility.Request;
import utility.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.UnresolvedAddressException;
import java.util.Set;

public class Client {
    private final String ip;
    private final int port;
    private Selector selector;


    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void connect() {
        SocketAddress address = new InetSocketAddress(ip, port);
        long start = System.currentTimeMillis();
        boolean flag = false;
        while (true) {
            try {
                SocketChannel channel = SocketChannel.open(address);
                channel.configureBlocking(false);
                selector = Selector.open();
                channel.register(selector, SelectionKey.OP_WRITE);
                break;
            } catch (IOException | UnresolvedAddressException ex) {
                long end = System.currentTimeMillis();
                if (end - start > 10000 && !flag) {
                    System.out.println("Ошибка подкдючения к северу");
                    flag = true;
                }
            }
        }

    }

    public void send(Request request) {
        try {
            ByteBuffer buffer = ByteBuffer.wrap(serialize(request));
            SocketChannel channel = null;
            while (channel == null) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                for (SelectionKey key : keys) {
                    if (key.isWritable()) {
                        channel = (SocketChannel) key.channel();
                        do {
                            channel.write(buffer);
                        } while (buffer.hasRemaining());
                        channel.register(selector, SelectionKey.OP_READ);
                        break;
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Response receive() throws IOException, ClassNotFoundException {
        SocketChannel channel = null;
        Response response = null;
        ByteBuffer buffer = ByteBuffer.allocate(16384);
        while (channel == null) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            for (SelectionKey key : keys) {
                if (key.isReadable()) {
                    channel =(SocketChannel) key.channel();
                    channel.read(buffer);
                    buffer.flip();
                    if (buffer.hasRemaining()) {
                        response = deserialize(buffer.array());
                    }
                    channel.register(selector, SelectionKey.OP_WRITE);
                    buffer.clear();
                    break;
                }
            }
        }
        return response;
    }

    public Response deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        Response response;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        response = (Response) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return response;
    }

    public byte[] serialize(Request request) throws IOException{
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        objectOutputStream.close();
        return buffer;
    }


}
