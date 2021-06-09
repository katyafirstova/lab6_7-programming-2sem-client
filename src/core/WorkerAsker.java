package core;

import model.Color;
import model.Status;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * {code WorkerAsker}
 */
public class WorkerAsker {

    public String askName() {

        String name = new String();
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        try {
            System.out.println("Введите имя:");
            name = scanner.nextLine().trim();
            if (name.equals("")) {
                throw new IllegalArgumentException("Имя не может быть пустым!");
            }

        } catch (Exception e) {
            System.out.format("Значение не распознано: %s\n", e.getMessage() == null ? "" : e.getMessage());
            return askName();
        }
        System.out.format("Вы ввели значение: %s\n", name);
        return name;
    }


    public float askX() {

        float x = 0.0f;
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        try {
            System.out.println("Введите координату X:");
            x = scanner.nextFloat();
            System.out.format("Вы ввели значение: %s\n", x);
            if (x == 0) throw new IllegalArgumentException("Поле не может быть пустым!");
        } catch (Exception e) {
            System.out.format("Значение не распознано: %s\n", e.getMessage() == null ? "" : e.getMessage());
            return askX();
        }
        return (float) x;

    }

    public int askY() {
        int y = 0;
        Scanner sc = new Scanner(System.in).useDelimiter("\n");

        try {

            System.out.println("Введите координату Y(максимальное значение - 92):");
            y = sc.nextInt();
            System.out.format("Вы ввели значение: %s\n", y);
            if (y == 0) throw new IllegalArgumentException("Поле не может быть пустым!");
            if (y > 92) throw new IllegalArgumentException("Значение поля не может превышать 92!");
        } catch (Exception e) {
            System.out.format("Координата не распознана: %s\n", e.getMessage() == null ? "" : e.getMessage());
            return askY();
        }

        return y;
    }


    public float askHeight() {
        float height;
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        try {

            System.out.println("Введите значение роста:");
            height = scanner.nextFloat();
            System.out.format("Вы ввели значение: %s\n", height);
            if (height < 0) throw new IllegalArgumentException("Значение поля не может быть " +
                    "меньше нуля или равно нулю!");
            if (height == 0) throw new IllegalArgumentException("Значение поля не должно быть равным нулю!");
        } catch (Exception e) {
            System.out.format("Значение поля не распознано: %s\n", e.getMessage() == null ? "" : e.getMessage());
            return askHeight();
        }
        return height;
    }


    public int askWeight() {

        int weight;
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        try {
            System.out.println("Введите значение веса:");
            weight = scanner.nextInt();
            System.out.format("Вы ввели значение: %s\n", weight);
            if (weight < 0) throw new IllegalArgumentException("Значение поля не может быть меньше нуля!");
        } catch (Exception e) {
            System.out.format("Значение поля не распознано: %s\n", e.getMessage() == null ? "" : e.getMessage());
            return askWeight();
        }
        return weight;

    }

    public Color askColor() {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        System.out.println("Введите одно из возможных значений для цвета волос:" + "\n" + Color.BLACK + "\n" +
                Color.BROWN + "\n" + Color.WHITE);
        String c = scanner.next();
        Color color = Color.fromStr(c);
        if (color == null || (color != null && (!color.equals(Color.BLACK) &&
                !color.equals(Color.BROWN) &&
                !color.equals(Color.WHITE)))) {
            System.out.println("Некорректный ввод");
            return askColor();
        }

        System.out.format("Вы ввели значение: %s\n", c);

        return color;
    }

    public Status askStatus() {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        System.out.println("Введите одно из возможных значений для статуса:" + "\n" + Status.FIRED + "\n"
                + Status.RECOMMENDED_FOR_PROMOTION + "\n" + Status.PROBATION + "\n" +
                Status.REGULAR + "\n" + Status.HIRED);
        String st = scanner.next();
        Status status = Status.fromStr(st);
        if (status == null || (status != null && (!status.equals(Status.HIRED) && !status.equals(Status.FIRED)
                && !status.equals(Status.PROBATION) &&
                !status.equals(Status.RECOMMENDED_FOR_PROMOTION) && !status.equals(Status.REGULAR)))) {
            System.out.println("Некорректный ввод");
            return askStatus();
        }
        System.out.format("Вы ввели значение: %s\n", st);

        return status;

    }

    public int askSalary() {

        int salary = 0;
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        try {
            System.out.println("Введите значение зарплаты");
            salary = scanner.nextInt();
            System.out.format("Вы ввели значение: %s\n", salary);
            if (salary == 0) throw new IllegalArgumentException("Значение поля не может равняться нулю!");
            if (salary < 0) throw new IllegalArgumentException("Значение поля не может быть меньше нуля!");
        } catch (Exception e) {
            System.out.format("Значение поля не распознано: %s\n", e.getMessage() == null ? "" : e.getMessage());
            return askSalary();
        }
        return salary;
    }

    public LocalDate askStartDate() {

        LocalDate newStartDate = null;
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        try {
            String startDate;

            System.out.println("Введите значение поля StartDate в формате год-месяц-число:");
            startDate = scanner.next();

            newStartDate = LocalDate.parse(startDate);
            System.out.println("Введена дата : " + newStartDate);
            if (newStartDate == null) throw new IllegalArgumentException();

        } catch (Exception e) {
            System.out.println("Некорректный формат ввода");
            return askStartDate();
        }
        return newStartDate;
    }

    public Date askEndDate() {

        Date newEndDate = null;
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        try {

            String endDate;
            System.out.println("Введите значение поля EndDate в формате год-месяц-число часы:минуты:секунды");
            endDate = scanner.next();
            System.out.println("Введена дата : " + endDate);
            newEndDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate);

        } catch (Exception e) {
            System.out.println("Некорректный формат ввода");
            return askEndDate();
        }
        return newEndDate;
    }


    public long askKey() {
        long key;
        try {
            Scanner scanner = new Scanner(System.in).useDelimiter("\n");
            System.out.println("Введите значение ключа для удаления элемента из коллекции:");
            key = scanner.nextLong();
            System.out.format("Вы ввели значение: %s\n", key);
            if (key < 0) throw new IllegalArgumentException();
        } catch (Exception e) {
            System.out.println("Некорректный формат ввода");
            return askKey();
        }
        return key;
    }

    public long askId() {
        long id;

        try {
            Scanner scanner = new Scanner(System.in).useDelimiter("\n");
            System.out.println("Введите значение ID:");
            id = scanner.nextLong();
            if (id < 0) throw new IllegalArgumentException();
        } catch (Exception e) {
            System.out.println("Некорректный формат ввода");
            return askId();
        }
        return id;
    }

    public String askFileName() {
        String name = new String();
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        try {
            System.out.println("Введите имя файла с расширением .xml:");
            name = scanner.nextLine();
            System.out.format("Вы ввели значение: %s\n", name);
            if (!name.contains(".xml")) throw new IllegalArgumentException("Имя файла должно содержать расшиение .xml");
            if (name.length() < 5) throw new IllegalArgumentException("Введите полное имя файла:");
        } catch (Exception e) {
            System.out.format("Значение поля не распознано: %s\n", e.getMessage() == null ? "" : e.getMessage());
            return null;
        }
        return name;
    }

    public String askNameOfFile() {
        String name = new String();
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        try {
            System.out.println("Введите имя файла:");
            name = scanner.nextLine();
            System.out.format("Вы ввели значение: %s\n", name);
        } catch (Exception e) {
            System.out.format("Значение поля не распознано: %s\n", e.getMessage() == null ? "" : e.getMessage());
            return null;
        }
        return name;
    }

    public String askUserName() {
        String userName = new String();
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        try {
            System.out.println("Введите имя пользователя:");
            userName = scanner.nextLine();
            System.out.format("Вы ввели значение: %s\n", userName);
        } catch (Exception e) {
            System.out.format("Значение поля не распознано: %s\n", e.getMessage() == null ? "" : e.getMessage());
            return null;
        }
        return userName;
    }

    public String askUserPassword() {
        String password = new String();
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        try {
            System.out.println("Введите пароль:");
            password = scanner.nextLine();
            System.out.format("Вы ввели значение: %s\n", password);
        } catch (Exception e) {
            System.out.format("Значение поля не распознано: %s\n", e.getMessage() == null ? "" : e.getMessage());
            return null;
        }
        return password;
    }



}
