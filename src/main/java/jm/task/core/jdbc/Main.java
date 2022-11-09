package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SessionFactory;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        UserService userServiceImpl = new UserServiceImpl();

        try {
            userServiceImpl.createUsersTable();

            userServiceImpl.saveUser("Freddie", "Mercury",(byte) 45);
            userServiceImpl.saveUser("Александр", "Пушкин",(byte) 38);
            userServiceImpl.saveUser("Nicole", "Kidman",(byte) 55);

            userServiceImpl.saveUser("Виталий", "Наливкин",(byte) 47);
            userServiceImpl.getAllUsers().forEach(U -> System.out.println(U.toString()));

            userServiceImpl.cleanUsersTable();

            userServiceImpl.dropUsersTable();

        }catch (Exception e) {
            e.getStackTrace();
        } finally {
            Util.SessionFactoryClose();
        }
    }
}
