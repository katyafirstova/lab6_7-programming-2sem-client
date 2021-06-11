package core;

import model.Hasher;
import model.User;

import javax.swing.*;
import java.io.Console;
import java.util.Scanner;

public class UserAsker {

    public String askUserName() {
        String userName = new String();
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        try {
            System.out.println("Введите имя пользователя:");
            userName = scanner.nextLine();
            System.out.format("Вы ввели значение: %s\n", userName);
        } catch (Exception e) {
            System.out.format("Значение не распознано: %s\n", e.getMessage() == null ? "" : e.getMessage());
            return null;
        }
        return userName;
    }

    public String askUserPassword() {
        Console cnsl = System.console();
        System.out.println("Введите пароль:");
        char[] pwd = cnsl.readPassword(": ");
        System.out.println("Password is: " + pwd);
//        Console cons;
//        String password;
//        if ((cons = System.console()) != null &&
//                (password = cons.readPassword("%s", "Password:")) != null) {
//            java.util.Arrays.fill(password, ' ');
//        }


//        try {
//            System.out.println("Введите пароль:");
//            password = scanner.nextLine();
//        } catch (Exception e) {
//            System.out.format("Значение не распознано: %s\n", e.getMessage() == null ? "" : e.getMessage());
//            return null;
//        }
//        return password;
        return pwd.toString();
    }

    public User getUser() {
        String userName = askUserName();
        String userPassword = askUserPassword();
        return new User(userName, Hasher.getHash(userPassword));
    }
}
