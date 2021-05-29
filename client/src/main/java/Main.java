import utility.Client;
import utility.ElementCreation;
import utility.ProgramProcess;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String ip = "localhost";
        int port = 2810;
        Scanner scanner = new Scanner(System.in);
        ElementCreation elementCreation = new ElementCreation(scanner);
        System.out.println("Клиент запущен");
        System.out.println();
        System.out.println("Введите IP-адресс сервера");
        try {
            ip = scanner.nextLine().trim();
            while (true) {
                System.out.println();
                System.out.println("Введите порт сервера");
                try {
                    port = Integer.parseInt(scanner.nextLine().trim());
                    break;
                } catch (NumberFormatException ex) {
                    System.out.println();
                    System.out.println("Неверный формат данных. Повторите ввод");
                }
            }
        } catch (NoSuchElementException ex) {
            System.exit(0);
        }
        Client client = new Client(ip, port);
        ProgramProcess programProcess = new ProgramProcess(elementCreation, scanner, client);
        programProcess.process();
    }
}