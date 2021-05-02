package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
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
            client.receive(buffer);
            buffer.flip();
            client.close();
        }
    }

    public static void main(String [] args) {
        try {

            String cmd = "command from console";
//            Message msg = new Message(cmd);
            Client client = new Client();
            client.sendMessage(cmd);
        } catch (IOException e) {
            LOG.info(e.getLocalizedMessage());
        }
    }
}