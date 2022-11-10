package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;



import javax.persistence.TypedQuery;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private String createUTsql = "CREATE TABLE IF NOT EXISTS  userstable (`Id` INT NOT NULL AUTO_INCREMENT," +
            "`Name` VARCHAR(100) NULL,`Lastname` VARCHAR(100) NULL,`Age` INT NULL, PRIMARY KEY (`Id`));";
    private String dropUTsql = "DROP TABLE IF EXISTS userstable;";
    private Transaction transaction;
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        try (Session session = Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            session.createSQLQuery(createUTsql).executeUpdate();
            transaction.commit();

        } catch (Exception e) {
            transactionRollBack();
            e.getStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {

        try (Session session = Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            session.createSQLQuery(dropUTsql).executeUpdate();
            transaction.commit();

        } catch (Exception e) {
            transactionRollBack();
            e.getStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User tempUser = new User(name, lastName, age);

        try (Session session = Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            session.save(tempUser);
            transaction.commit();

            System.out.println("User с именем – " + name + " добавлен в базу данных");

        } catch (Exception e) {
            transactionRollBack();
            e.getStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {

        try (Session session = Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            session.remove(session.get(User.class, id));
            transaction.commit();

        } catch (Exception e) {
            transactionRollBack();
            e.getStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {

        List<User> usersList = null;

        try (Session session = Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            TypedQuery<User> tQuery = session.createQuery("from User", User.class);
            usersList = tQuery.getResultList();

            transaction.commit();

            return usersList;
        } catch (Exception e) {
            transactionRollBack();
            e.getStackTrace();
        }
            return usersList;
        }



    @Override
    public void cleanUsersTable() {

        try (Session session = Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            transaction.commit();

        } catch (Exception e) {
            transactionRollBack();
            e.getStackTrace();
        }
    }

    public void transactionRollBack() {
        if (transaction != null) {
            transaction.rollback();
        }
    }
}

