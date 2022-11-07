package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private String createUTsql = "CREATE TABLE userstable (`Id` INT NOT NULL AUTO_INCREMENT," +
            "`Name` VARCHAR(100) NULL,`Lastname` VARCHAR(100) NULL,`Age` INT NULL, PRIMARY KEY (`Id`));";
    private String dropUTsql = "DROP TABLE userstable;";
    private String saveUsersql = "INSERT INTO userstable (Name, Lastname, Age) VALUES (?, ?, ?);";
    private String deleteUsersql = "DELETE FROM userstable WHERE id=?";
    private String getAllUserssql = "SELECT * FROM userstable";
    private String cleanUTsql = "DELETE FROM userstable";
    private String existsTablesql = "SELECT count(*) FROM information_schema.tables WHERE table_name = ? ;";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        boolean isExist = existsTable();

        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {

            if (!existsTable()) {
                statement.executeUpdate(createUTsql);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() throws SQLException {
        boolean isExist = existsTable();
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {

            if (isExist) {
                statement.executeUpdate(dropUTsql);
            }
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

    /*
       Замечание от ментора:
       В getAllUsers() вместо Statement используй PreparedStatement, для изучения
       https://mkyong.com/tutorials/jdbc-tutorials/
       , я не понимаю чем указанный там способ отличается от моего, только самим фактом наличия PreparedStatement?
       https://mkyong.com/jdbc/jdbc-preparestatement-example-select-list-of-the-records/
   */
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

    private boolean existsTable() throws SQLException {
        int exist;

        try (Connection connection = Util.getConnection();
             PreparedStatement pStatement = connection.prepareStatement(existsTablesql)) {

            pStatement.setString(1, "userstable");
            ResultSet resultSet = pStatement.executeQuery();
            resultSet.next();
            exist = resultSet.getInt(1);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  exist != 0;
    }
}
