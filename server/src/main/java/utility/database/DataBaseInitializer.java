package utility.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseInitializer {
    private final Connection connection;

    public DataBaseInitializer(DataBaseConnection dataBaseConnection) {
        this.connection = dataBaseConnection.getConnection();
    }

    public void initializeTables() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users(" +
                    "username varchar(255)," +
                    "password BYTEA DEFAULT (null))");
            statement.executeUpdate("CREATE SEQUENCE IF NOT EXISTS ids START 1");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS dragons(" +
                    "id bigint PRIMARY KEY," +
                    "name varchar(255) NOT NULL," +
                    "x_coordinate int NOT NULL," +
                    "y_coordinate int NOT NULL," +
                    "creation_date date default (current_date)," +
                    "age bigint NOT NULL CHECK (age > 0)," +
                    "color varchar(255) NOT NULL," +
                    "dragon_type varchar(255) NOT NULL," +
                    "dragon_character varchar(255) NOT NULL," +
                    "size int," +
                    "eyes_count double precision NOT NULL," +
                    "owner varchar(255))");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}
