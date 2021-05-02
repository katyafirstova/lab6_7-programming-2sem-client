import java.io.*;
import java.util.*;


public class Message implements Serializable {
    FileOutputStream fos = new FileOutputStream("temp.out");
    ObjectOutputStream oos = new ObjectOutputStream(fos);
    String cmd = new String();
    





    public Message() throws IOException {
    }
}
