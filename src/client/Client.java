package client;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import core.CLICollection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Client {


    static final Logger LOG = LoggerFactory.getLogger(Client.class);
    static final int PORT_SERVER = 9023;
    static final int PORT_CLIENT = 43245;
    static final String LOCALHOST = "localhost";
    private final String serverAddr;

    public Client(String serverAddr) {
        this.serverAddr = serverAddr == null ? LOCALHOST : serverAddr;
    }

    public Client() {
        this.serverAddr = LOCALHOST;
    }


    public void sendMessage(ByteBuffer msg) throws IOException {
        if (msg != null) {
            DatagramChannel client = null;
            client = DatagramChannel.open();
            client.bind(null);
            InetSocketAddress serverAddress = new InetSocketAddress(this.serverAddr, PORT_SERVER);
            client.send(msg, serverAddress);
            client.close();
        }
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
        DatagramChannel server = DatagramChannel.open();
        server.configureBlocking(true);
        InetSocketAddress iAdd = new InetSocketAddress(this.serverAddr, PORT_CLIENT);
        server.bind(iAdd);
        ByteBuffer in = ByteBuffer.allocate(81920);
        server.receive(in);
        server.close();
        return in;
    }
}