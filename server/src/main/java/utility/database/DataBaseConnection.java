package utility.database;

import utility.ConsoleColor;
import utility.ExitException;

import java.sql.*;

public class DataBaseConnection {
    private final Connection connection;
    private String url;
    private String login;
    private String password;


    public DataBaseConnection() throws SQLException, ClassNotFoundException {
            checkVariables();
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, login, password);
            System.out.println("Подключение к базе данных установлено");
    }

    public void checkVariables() {
        if (System.getenv().get("URL") == null) {
            System.out.println(ConsoleColor.ANSI_RED.getColor() + "Environment variable URL was not found" + ConsoleColor.ANSI_RESET.getColor());
            throw new ExitException();
        }
        if (System.getenv().get("LOGIN") == null) {
            System.out.println(ConsoleColor.ANSI_RED.getColor() + "Environment variable USERNAME was not found" + ConsoleColor.ANSI_RESET.getColor());
            throw new ExitException();
        }
        if (System.getenv().get("PASSWORD") == null) {
            System.out.println(ConsoleColor.ANSI_RED.getColor() + "Environment variable PASSWORD was not found" + ConsoleColor.ANSI_RESET.getColor());
            throw new ExitException();
        }
        this.url = System.getenv("URL");
        this.login = System.getenv("LOGIN");
        this.password = System.getenv("PASSWORD");
    }

    public Connection getConnection() {
        return connection;
    }

}
