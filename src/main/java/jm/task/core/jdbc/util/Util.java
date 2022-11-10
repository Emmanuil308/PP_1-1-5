package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util  {
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static Connection connection;
    private static SessionFactory sessionFactory;

    public static Connection getConnection () throws SQLException {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        } catch (SQLException e) {
            System.out.println("Не удалось установить соединение");
        }

        return connection;
    }

    public static SessionFactory getSessionFactory () {
        if (sessionFactory == null) {

                Properties properties = new Properties();
                properties.put(Environment.URL , URL);
                properties.put(Environment.USER, USERNAME);
                properties.put(Environment.PASS, PASSWORD);
                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                properties.put(Environment.SHOW_SQL, false);
                properties.put(Environment.FORMAT_SQL, false);

                sessionFactory = new Configuration().addAnnotatedClass(User.class)
                        .setProperties(properties).buildSessionFactory();
        }
        return sessionFactory;
    }

    public  static void SessionFactoryClose() {
        sessionFactory.close();
    }

}
