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
        String userName = null;
        String userPassword = null;
        while( userName == null || userName.length() == 0) {
            userName = askUserName();
            if (userName == null || userName.length() == 0) {
                System.out.println("Имя не может быть пустым");
            }
        }
        while( userPassword == null || userPassword.length() == 0) {
            userPassword = askUserPassword();
            if (userPassword == null || userPassword.length() == 0) {
                System.out.println("Пароль не может быть пустым");
            }
        }
        return new User(userName, Hasher.getHash(userPassword));
    }
}
