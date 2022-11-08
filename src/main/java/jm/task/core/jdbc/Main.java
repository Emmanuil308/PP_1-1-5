package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        UserServiceImpl userServiceImpl = new UserServiceImpl();

        userServiceImpl.createUsersTable();

        userServiceImpl.saveUser("Freddie", "Mercury",(byte) 45);
        userServiceImpl.saveUser("Александр", "Пушкин",(byte) 38);
        userServiceImpl.saveUser("Nicole", "Kidman",(byte) 55);

        userServiceImpl.saveUser("Виталий", "Наливкин",(byte) 47);
        userServiceImpl.getAllUsers().forEach(U -> System.out.println(U.toString()));

        userServiceImpl.cleanUsersTable();

        userServiceImpl.dropUsersTable();

    }
}
