import java.io.*;


public class Message implements Serializable {
    String cmd = new String();

    public Message(String cmd) {
        this.cmd = cmd;

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String cmd = new String("testteeweqt");
        serialize(cmd);
    }

    static void serialize(String cmd) {
        try (FileOutputStream fos = new FileOutputStream("savedMessage");
             ObjectOutputStream oos = new ObjectOutputStream(fos);) {
             oos.writeObject(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

