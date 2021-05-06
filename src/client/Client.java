package client;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import core.CLICollection;
import model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Client {

    static final Logger LOG = LoggerFactory.getLogger(Client.class);
    static final int PORT = 9023;
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

    public static void main(String [] args) {
        Client client = new Client();
        client.run();
    }

    public void run() {
        CLICollection cliCollection = new CLICollection();
        cliCollection.start();
    }

    public void serialize(Message message) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos);) {
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
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
}