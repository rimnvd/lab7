package utility.database;

import data.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.Vector;

public class DataBaseCollectionManager {

    private final Connection connection;

    public DataBaseCollectionManager(DataBaseConnection dataBaseConnection) {
        this.connection = dataBaseConnection.getConnection();
    }

    public Dragon getDragonFromDB(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        LocalDate creationDate = resultSet.getDate("creation_date").toLocalDate();
        int age = resultSet.getInt("age");
        Color color = Color.valueOf(resultSet.getString("color").toUpperCase());
        DragonType type = DragonType.valueOf(resultSet.getString("dragon_type").toUpperCase());
        DragonCharacter character = DragonCharacter.valueOf(resultSet.getString("dragon_character").toUpperCase());
        Integer x = resultSet.getInt("x_coordinate");
        Integer y = resultSet.getInt("y_coordinate");
        DragonHead head;
        Double eyesCount = resultSet.getDouble("eyes_count");
        if (resultSet.getInt("size") == 0) head = new DragonHead(eyesCount);
        else head = new DragonHead(resultSet.getInt("size"), eyesCount);
        Coordinates coordinates = new Coordinates(x, y);
        String owner = resultSet.getString("owner");
        return new Dragon(id, creationDate, name, age, type, color, character, head, coordinates, owner);
    }

    public void loadCollectionFromDB(Vector<Dragon> vector) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM dragons");
        while (resultSet.next()) {
            Dragon dragon = getDragonFromDB(resultSet);
            vector.add(dragon);
        }
        statement.close();

    }

    public void clear(String username) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM dragons WHERE owner = ?");
            preparedStatement.setString(1, username);
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void removeById(String username, Long id) throws NoPermissionException, SQLException, NoIdException {
        checkUsernameAndId(username, id);
        PreparedStatement delete = connection.prepareStatement("DELETE FROM dragons WHERE id = ?");
        delete.setLong(1, id);
        delete.executeUpdate();
    }

    public Long insertDragon(Dragon dragon, String username) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO dragons values (nextval('id_sequence'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            setDragon(preparedStatement, dragon);
            preparedStatement.setString(11, username);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("SELECT last_value FROM id_sequence");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("last_value");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }

    public void updateById(String username, Long id, Dragon dragon) throws NoPermissionException, NoIdException, SQLException {
        checkUsernameAndId(username, id);
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE dragons SET name = ?, x_coordinate = ?, y_coordinate = ?, creation_date = ?, age = ?, " +
                "color = ?, dragon_type = ?, dragon_character = ?,  size = ?, eyes_count = ? WHERE id = ?");
        setDragon(preparedStatement, dragon);
        preparedStatement.setLong(11, id);
        preparedStatement.executeUpdate();
    }

    public void checkUsernameAndId(String username, Long id) throws NoPermissionException, NoIdException, SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM dragons WHERE id = ?");
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) throw new NoIdException();
        if (!resultSet.getString("username").equals(username)) throw new NoPermissionException();
    }

    public void setDragon(PreparedStatement preparedStatement, Dragon dragon) throws SQLException {
        preparedStatement.setString(1, dragon.getName());
        preparedStatement.setInt(2, dragon.getCoordinates().getX());
        preparedStatement.setInt(3, dragon.getCoordinates().getY());
        preparedStatement.setDate(4, Date.valueOf(dragon.getCreationDate()));
        preparedStatement.setLong(5, dragon.getAge());
        preparedStatement.setString(6, dragon.getColor().toString());
        preparedStatement.setString(7, dragon.getType().toString());
        preparedStatement.setString(8, dragon.getCharacter().toString());
        preparedStatement.setInt(9, dragon.getHead().getSize() != null ? dragon.getHead().getSize() : Types.NULL);
        preparedStatement.setDouble(10, dragon.getHead().getEyesCount() != null ? dragon.getHead().getEyesCount() : Types.NULL);
    }

    public boolean removeByColor(String username, Color color) {
        try {
            PreparedStatement chooseDragons = connection.prepareStatement("SELECT * from dragons where color = ? AND owner = ?");
            chooseDragons.setString(1, color.toString());
            chooseDragons.setString(2, username);
            ResultSet resultSet = chooseDragons.executeQuery();
            Long firstId = resultSet.getLong("id");
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM dragons WHERE id = ?");
            preparedStatement.setLong(1, firstId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void removeLast(String username) throws NoPermissionException, SQLException {
        PreparedStatement lastId = connection.prepareStatement("SELECT last_value from id_sequence");
        ResultSet resultId = lastId.executeQuery();
        PreparedStatement chooseDragon = connection.prepareStatement("SELECT * FROM dragons where id = ?");
        chooseDragon.setLong(1, resultId.getLong("last_value"));
        ResultSet dragon = chooseDragon.executeQuery();
        if (!dragon.getString("owner").equals(username)) throw new NoPermissionException();
        PreparedStatement delete = connection.prepareStatement("DELETE FROM dragons WHERE id = ?");
        delete.setLong(1, resultId.getLong("last_value"));
        delete.executeUpdate();
    }


}



