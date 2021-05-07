package core;

import client.Client;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import model.CommandCollection;
import model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * {@code CLICollection} Command line interface
 */

public class CLICollection {

    private ArrayList<String> history = new ArrayList<String>();
    static final Logger LOG = LoggerFactory.getLogger(Client.class);


    public void analyse(String cmd) {

        CommandCollection command = CommandCollection.fromCmd(cmd == null ? "" : cmd.trim());

        switch (command) {

            case HELP:
                addAndSaveHistory(command.getCommand());
                for (CommandCollection s : CommandCollection.values()) {
                    if (s != CommandCollection.UNKNOWN && s != CommandCollection.SAVE) {
                        System.out.println(s.getCommand() + ": " + s.getDescription());
                    }
                }
                break;

            case INSERT:
                addAndSaveHistory(command.getCommand());
                sendMessage(new Message(command, createWorker()));
                break;

            case CLEAR:
                addAndSaveHistory(command.getCommand());
                sendMessage(new Message(command));
                break;
            case SHOW:
                addAndSaveHistory(command.getCommand());
                sendMessage(new Message(command));


                break;
            case INFO:
                addAndSaveHistory(command.getCommand());
                sendMessage(new Message(command));
                break;

//            case UPDATE_ID:
////                collection.update(getId());
//                addAndSaveHistory(command.getCommand());
//                sendMessage(new Message(command, ));
//                break;


            case REMOVE_LOWER:
            case REMOVE_GREATER:
                addAndSaveHistory(command.getCommand());
                sendMessage(new Message(command, getRemoveSalary()));
                break;

            case REMOVE_KEY:
                addAndSaveHistory(command.getCommand());
                sendMessage(new Message(command, getRemoveKey()));
                break;

            case REMOVE_ALL_BY_START_DATE:
                addAndSaveHistory(command.getCommand());
                sendMessage(new Message(command, getStartDate()));
                break;

            case REMOVE_ALL_BY_END_DATE:
            case PRINT_FIELD_DESCENDING_END_DATE:
                addAndSaveHistory(command.getCommand());
                sendMessage(new Message(command, getEndDate()));
                break;


            case EXIT:
                addAndSaveHistory(command.getCommand());
                System.exit(0);
                break;

            case HISTORY:
                showHistory();
                break;

            case EXECUTE_SCRIPT:
                executeScript();
                break;

            case UNKNOWN:
                System.out.println(CommandCollection.UNKNOWN.getDescription());
                break;

        }
    }

    /**
     * execute script
     */
    private void executeScript() {
        XStream xstream = new XStream(new StaxDriver());
        XStream.setupDefaultSecurity(xstream);
        try {
            WorkerAsker workerAsker = new WorkerAsker();
            String filename = workerAsker.askNameOfFile();
            if (filename != null) {
                BufferedReader reader = new BufferedReader(new FileReader(filename));
                ArrayList<String> script = new ArrayList<String>();
                String line;
                while ((line = reader.readLine()) != null) {
                    script.add(line);
                    for (int i = 0; i < script.size(); i++) {
                        analyse(script.get(i));
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@code showHistory} Вывод истории использованных команд
     */
    private void showHistory() {
        for (int i = 0; i < history.size(); i++) {
            String hist = history.get(i);
            if (!hist.equals(CommandCollection.UNKNOWN)) {
                System.out.println(hist);

            }
        }
    }

    /**
     * {@code addAndSaveHistory} Сохранение истории команд в файл в формате xml
     */
    private void addAndSaveHistory(String command) {
        history.add(command);
        XStream xstream = new XStream(new StaxDriver());
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("history.xml"));
            xstream.toXML(history, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Date getEndDate() {
        WorkerAsker workerAsker = new WorkerAsker();
        return workerAsker.askEndDate();
    }

    private LocalDate getStartDate() {
        WorkerAsker workerAsker = new WorkerAsker();
        return workerAsker.askStartDate();
    }


    private long getRemoveKey() {
        WorkerAsker workerAsker = new WorkerAsker();
        return workerAsker.askKey();
    }

    private int getRemoveSalary() {
        WorkerAsker workerAsker = new WorkerAsker();
        return workerAsker.askSalary();
    }

    private long getId() {

        WorkerAsker workerAsker = new WorkerAsker();
        return workerAsker.askId();
    }


    /**
     * {@code сreateWorker} Создание элемента коллекции
     */
    private Worker createWorker() {

        WorkerAsker workerAsker = new WorkerAsker();
        String name = workerAsker.askName();
        float x = workerAsker.askX();
        int y = workerAsker.askY();
        float height = workerAsker.askHeight();
        int weight = workerAsker.askWeight();
        Status status = workerAsker.askStatus();
        Color color = workerAsker.askColor();
        int salary = workerAsker.askSalary();
        LocalDate newStartDate = workerAsker.askStartDate();
        Date newEndDate = workerAsker.askEndDate();
        Worker worker = WorkerFabric.create(name, x, y, salary, newStartDate, newEndDate, status, height, weight, color);
        System.out.format("Создан элемент коллекции: %s\n", worker);

        return worker;
    }

    /**
     * {@code createFilename} создание имя файла
     */
    private String createFilename() {
        WorkerAsker workerAsker = new WorkerAsker();
        String filename = workerAsker.askFileName();
        if (filename != null && new File(filename).isFile()) {
            return filename;
        }
        System.out.println("Некорректное имя файла");
        return null;
    }

    private Worker getWorker() {
        return new Worker();
    }

    /**
     * {@code start} Считывание и анализ вводимой пользователем строки
     */
    public void start() {

        for (; ; ) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                String line = bufferedReader.readLine();
                analyse(line);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void sendMessage(Message message) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos);) {
            oos.writeObject(message);
            Client client = new Client();
            ByteBuffer buffer = ByteBuffer.wrap(baos.toByteArray());
            client.sendMessage(buffer);
            LOG.info("Send message to server");
            LOG.info(buffer.toString());
        } catch (IOException e) {
            LOG.info(e.getLocalizedMessage());
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

    public Message receiveMessage() throws IOException {
        Client client = new Client();
        ByteBuffer buffer = client.receiveMessage();
        return deserialize(buffer);
    }

    public void show(HashMap<Long, Worker> workers) {
        System.out.println("___________");
        for (Map.Entry<Long, Worker> entry : workers.entrySet()) {
            System.out.format("key: %d, worker: %s\n", entry.getKey(), entry.getValue());
        }
        System.out.println("===========");
    }
}









