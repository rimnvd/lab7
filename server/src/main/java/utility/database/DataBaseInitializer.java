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
                    "username varchar(255) PRIMARY KEY," +
                    "password BYTEA DEFAULT (null))");
            statement.executeUpdate("CREATE SEQUENCE IF NOT EXISTS ids START 1");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}
