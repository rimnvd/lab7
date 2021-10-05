package utility;


import commands.ResultCode;
import exceptions.ServerUnavailableException;

import java.io.Console;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Authorization {
    private final Scanner scanner;
    private final Client client;
    private Session session = null;

    public Authorization(Scanner scanner, Client client) {
        this.scanner = scanner;
        this.client = client;
    }

    public String inputName() {
        System.out.println("Введите имя пользователя:");
        String name = "";
        while (name.isEmpty()) {
            name = scanner.nextLine().trim();
            System.out.println();
        }
        return name;
    }

    public String inputPassword() {
        System.out.println("Введите пароль:");
        String password = "";
        while (password.isEmpty()) {
            Console console = System.console();
            if (console != null) {
                char[] symbols = console.readPassword();
                if (symbols == null) continue;
                password = String.valueOf(symbols);
            } else {
                password = scanner.nextLine();
            }
            System.out.println();
        }
        return password;
    }

    public boolean checkRegistration() throws NoSuchElementException {
        while (true) {
            System.out.println("У вас уже есть учетная запись?(Y/N)");
            String answer = scanner.nextLine().trim();
            System.out.println();
            if (answer.equalsIgnoreCase("Y")) return true;
            else if (answer.equalsIgnoreCase("N")) return false;
        }
    }

    public void authorize() throws NoSuchElementException {
        while (true) {
            String name = inputName();
            String password = inputPassword();
            Request request = new Request(null, null, null, name, password, true);
            try {
                client.connect();
                client.send(request);
                Response response = client.receive();
                ResultCode resultCode = response.getResultCode();
                if (resultCode == ResultCode.WRONG) {
                    System.out.println(ConsoleColor.ANSI_RED.getColor() + "Неверное имя пользователя или пароль. Пожалуйста, повторите ввод" +
                            ConsoleColor.ANSI_RESET.getColor());
                    System.out.println();
                } else if (resultCode == ResultCode.OK) {
                    System.out.println("Пользователь успешно авторизован");
                    session = new Session(name, password);
                    break;
                } else System.out.println(ConsoleColor.ANSI_RED.getColor() + "Ошибка при работе с базой данных" + ConsoleColor.ANSI_RESET.getColor());
            } catch (IOException | ClassNotFoundException | ServerUnavailableException e) {
                System.out.println((ConsoleColor.ANSI_RED.getColor() + "Ошибка подкдючения к северу" + ConsoleColor.ANSI_RESET.getColor()));
                System.out.println();
            }
        }
    }

    public void register() throws NoSuchElementException {
        while (true) {
            String name = inputName();
            Request request = new Request(null, null, null, name, null, false);
            try {
                client.connect();
                client.send(request);
                Response response = client.receive();
                ResultCode resultCode = response.getResultCode();
                if (resultCode == ResultCode.EXIST) {
                    System.out.println("Пользователь с таким именем уже существует. Пожалуйста, введите другое имя пользователя");
                    System.out.println();
                } else if (resultCode == ResultCode.OK){
                    String password = inputPassword();
                    request = new Request(null, null, null, name, password, false);
                    client.connect();
                    client.send(request);
                    response = client.receive();
                    if (response.getResultCode() == ResultCode.OK) {
                        System.out.println();
                        System.out.println("Пользователь успешно зарегистрирован. Пожалуйста, авторизуйтесь");
                        authorize();
                        break;
                    } else System.out.println(ConsoleColor.ANSI_RED.getColor() + "Ошибка при работе с базой данных" + ConsoleColor.ANSI_RESET.getColor());
                } else System.out.println(ConsoleColor.ANSI_RED.getColor() + "Ошибка подкдючения к северу" + ConsoleColor.ANSI_RESET.getColor());
            } catch (IOException | ClassNotFoundException | ServerUnavailableException e) {
                System.out.println(ConsoleColor.ANSI_RED.getColor() + "Ошибка подкдючения к северу" + ConsoleColor.ANSI_RESET.getColor());
                System.out.println();
            }
        }

    }

    public Session getSession() {
        return session;
    }

}