import utility.Client;
import utility.ElementCreation;
import utility.ProgramProcess;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String ip;
        int port;
        Scanner scanner = new Scanner(System.in);
        ElementCreation elementCreation = new ElementCreation(scanner);
        System.out.println("Клиентское приложение запущено");
        if (System.getenv().get("IP") == null) {
            System.out.println("\n" + "\u001B[31m" + "Переменная окружения IP не найдена" + "\u001B[0m");
            return;
        } else ip = System.getenv("IP");
        if (System.getenv().get("PORT") == null) {
            System.out.println("\n" + "\u001B[31m" + "Переменная окружения PORT не найдена" + "\u001B[0m");
            return;
        } else {
            try {
                port = Integer.parseInt(System.getenv("PORT"));
            } catch (NumberFormatException ex) {
                System.out.println("\n" + "\u001B[31m" + "Неверный формат порта" + "\u001B[0m");
                return;
            }
            if (port < 0 || port > 0xFFFF) {
                System.out.println("\n" + "\u001B[31m" + "Неверный формат порта" + "\u001B[0m");
                return;
            }
        }
        Client client = new Client(ip, port);
        ProgramProcess programProcess = new ProgramProcess(elementCreation, scanner, client);
        programProcess.process();
    }
}