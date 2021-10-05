package utility.database;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseUserManager {

    private Connection connection;

    public DataBaseUserManager (DataBaseConnection dataBaseConnection) {
        this.connection = dataBaseConnection.getConnection();

    }

    public boolean isUserExist(String username) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
        preparedStatement.setString(1, username);
        return preparedStatement.executeQuery().next();
    }

    public void register(String username, String password) throws SQLException, NoSuchAlgorithmException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
        preparedStatement.setString(1, username);
        preparedStatement.setBytes(2, getHash(password));
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public boolean signIn(String username, String password) throws SQLException, NoSuchAlgorithmException {
        if (!isUserExist(username)) return false;
        PreparedStatement signIn = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
        signIn.setString(1, username);
        signIn.setBytes(2, getHash(password));
        return signIn.executeQuery().next();
    }



    public byte[] getHash(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-224");
        byte[] data = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        return messageDigest.digest(data);
    }
}
