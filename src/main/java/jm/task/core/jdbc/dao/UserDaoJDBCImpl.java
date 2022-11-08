package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private String createUTsql = "CREATE TABLE IF NOT EXISTS  userstable (`Id` INT NOT NULL AUTO_INCREMENT," +
            "`Name` VARCHAR(100) NULL,`Lastname` VARCHAR(100) NULL,`Age` INT NULL, PRIMARY KEY (`Id`));";
    private String dropUTsql = "DROP TABLE IF EXISTS userstable;";
    private String saveUsersql = "INSERT INTO userstable (Name, Lastname, Age) VALUES (?, ?, ?);";
    private String deleteUsersql = "DELETE FROM userstable WHERE id=?";
    private String getAllUserssql = "SELECT * FROM userstable";
    private String cleanUTsql = "DELETE FROM userstable";
    private String existsTablesql = "SELECT count(*) FROM information_schema.tables WHERE table_name = ? ;";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {

            statement.executeUpdate(createUTsql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() {

        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(dropUTsql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement pStatement = connection.prepareStatement(saveUsersql)) {

            pStatement.setString(1, name);
            pStatement.setString(2, lastName);
            pStatement.setLong(3, age);

            pStatement.execute();
            System.out.println("User с именем – " + name + " добавлен в базу данных");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement pStatement = connection.prepareStatement(deleteUsersql)) {

            pStatement.setLong(1 , id);
            pStatement.execute();

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {

        List<User> usersList = new ArrayList<>();

        try (Connection connection = Util.getConnection();
        Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(getAllUserssql);

            while (resultSet.next()) {
                User userTemp = new User();
                userTemp.setId(resultSet.getLong("Id"));
                userTemp.setName(resultSet.getString("Name"));
                userTemp.setLastName(resultSet.getString("Lastname"));
                userTemp.setAge((byte) resultSet.getInt("age"));

                usersList.add(userTemp);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return usersList;
    }

    public void cleanUsersTable() {

        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {

            statement.executeUpdate(cleanUTsql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
