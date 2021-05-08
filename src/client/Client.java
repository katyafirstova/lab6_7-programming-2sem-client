package client;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import core.CLICollection;
import model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Client {

    static final Logger LOG = LoggerFactory.getLogger(Client.class);
    static final int PORT = 9023;
    static final int PORT_2 = 43245;
    static final String LOCALHOST = "localhost";
    private String serverAddr;

    public Client(String serverAddr) {
        this.serverAddr = serverAddr == null ? LOCALHOST : serverAddr;
    }

    public Client() {
        this.serverAddr = LOCALHOST;
    }


    public void sendMessage(String msg) throws IOException {
        if (msg != null) {
            DatagramChannel client = null;
            client = DatagramChannel.open();
            client.bind(null);
            ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
            InetSocketAddress serverAddress = new InetSocketAddress(this.serverAddr, PORT);
            client.send(buffer, serverAddress);
            buffer.clear();
            client.close();
        }
    }

    public void sendMessage(ByteBuffer msg) throws IOException {
        if (msg != null) {
            DatagramChannel client = null;
            client = DatagramChannel.open();
            client.bind(null);
            InetSocketAddress serverAddress = new InetSocketAddress(this.serverAddr, PORT);
            client.send(msg, serverAddress);
            Message message = deserialize(msg);
            msg.clear();
            client.close();
        }
    }

    public Message deserialize(ByteBuffer buffer) {
        Message message = null;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(buffer.array());
             ObjectInputStream in = new ObjectInputStream(bis);) {
            message = (Message) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            LOG.debug(e.getLocalizedMessage());
        }
        return message;
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

    public void run() {
        CLICollection cliCollection = new CLICollection();
        cliCollection.start();
    }


    public ByteBuffer receiveMessage() throws IOException {
        ByteBuffer buffer = null;
        try {
            DatagramChannel client = DatagramChannel.open();
//            client.configureBlocking(false);
            client.bind(null);
            InetSocketAddress receiver = new InetSocketAddress(this.serverAddr, PORT);
            client.connect(receiver);
            buffer = ByteBuffer.allocate(10240);
            client.receive(buffer);
            buffer.flip();
            client.close();
        } catch (IOException e) {
            LOG.info(e.getLocalizedMessage());
        }
        return buffer;
    }

}