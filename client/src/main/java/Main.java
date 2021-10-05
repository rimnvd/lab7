import utility.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String ip;
        int port;
        Scanner scanner = new Scanner(System.in);
        ElementCreation elementCreation = new ElementCreation(scanner);
        System.out.println("Клиентское приложение запущено");
        if (System.getenv().get("IP") == null) {
            System.out.println("\n" + ConsoleColor.ANSI_RED.getColor() + "Переменная окружения IP не найдена" + ConsoleColor.ANSI_RESET.getColor());
            return;
        } else ip = System.getenv("IP");
        if (System.getenv().get("PORT") == null) {
            System.out.println("\n" + ConsoleColor.ANSI_RED.getColor() + "Переменная окружения PORT не найдена" + ConsoleColor.ANSI_RESET.getColor());
            return;
        } else {
            try {
                port = Integer.parseInt(System.getenv("PORT"));
            } catch (NumberFormatException ex) {
                System.out.println("\n" + ConsoleColor.ANSI_RED.getColor() + "Неверный формат порта" + ConsoleColor.ANSI_RESET.getColor());
                return;
            }
            if (port < 0 || port > 0xFFFF) {
                System.out.println("\n" + ConsoleColor.ANSI_RED.getColor() + "Неверный формат порта" + ConsoleColor.ANSI_RESET.getColor());
                return;
            }
        }
        Client client = new Client(ip, port);
        Authorization authorization = new Authorization(scanner, client);
        ProgramProcess programProcess = new ProgramProcess(elementCreation, scanner, client, authorization);
        programProcess.process();
    }
}