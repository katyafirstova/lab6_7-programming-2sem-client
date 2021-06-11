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
        String userPassword = new String();
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        try {
            System.out.println("Введите пароль:");
            userPassword = scanner.nextLine();
        } catch (Exception e) {
            System.out.format("Значение не распознано: %s\n", e.getMessage() == null ? "" : e.getMessage());
            return null;
        }
        return userPassword;

    }

    public User getUser() {
        String userName = askUserName();
        String userPassword = askUserPassword();
        return new User(userName, Hasher.getHash(userPassword));
    }
}
