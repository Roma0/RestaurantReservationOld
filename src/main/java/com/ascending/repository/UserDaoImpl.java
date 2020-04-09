package com.ascending.repository;

import com.ascending.model.User;
import com.ascending.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public User save(User user) {
        Transaction transaction = null;
        User result = null;

        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.save(user);
            result = user;
            transaction.commit();
            session.close();
        }
        catch (Exception e){
            if(transaction != null)transaction.rollback();
            logger.error("Failure to save user.", e.getMessage());
        }
        if(result != null)logger.debug(String.format("The user %s was inserted into table.", result.toString()));
        return result;
    }

    @Override
    public User update(User user) {
        Transaction transaction = null;
        User result = null;

        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            result = user;
            transaction.commit();
            session.close();
        }catch (Exception e){
            if(transaction != null)transaction.rollback();
            logger.error("Failure to update user.", e.getMessage());
        }
        if(result != null)logger.debug(String.format("The user %s was updated in database.", result.getName()));
        return result;
    }

    @Override
    public boolean deleteById(Long id) {
        Transaction transaction = null;
        String hql = "DELETE User AS u WHERE u.id = :id";
        int result = 0;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query<User> query = session.createQuery(hql);
            query.setParameter("id", id);
            result = query.executeUpdate();
            transaction.commit();
            session.close();
            logger.debug(String.format("Deleted the user by id=%s.", id));
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Failure to delete user.", e.getMessage());
        }

        return result >= 1 ? true:false;
    }

    @Override
    public List<User> getUsers() {
        String hql = "FROM User";
        List<User> results = null;
        try(Session session = sessionFactory.openSession()){
            Query<User> query = session.createQuery(hql);
            results = query.getResultList();
            session.close();
        }catch (Exception e){
            logger.error("Failure to get users.", e.getMessage());
        }

        if(results != null)logger.debug("Got all %s users.", results.size());
        return results;
    }

    @Override
    public User getUserByNameOrEmail(String nameOrEmail) {
        String hql = "FROM User as u WHERE lower(u.name) = :name or lower(u.email) = :email";
        User result = null;

        try (Session session = sessionFactory.openSession()){
            Query<User> query = session.createQuery(hql);
            query.setParameter("name", nameOrEmail.toLowerCase());
            query.setParameter("email", nameOrEmail.toLowerCase());
            result = query.uniqueResult();
            session.close();
        }catch (Exception e){
            logger.error("Failure to get the user by name or email.", e.getMessage());
        }
        if (result != null) logger.debug(String.format("Got the user {%s} with name or email: %s.", result.toString(), nameOrEmail));
        return result;
    }

    @Override
    public User getUserByCredential(String nameOrEmail, String password) {
        String hql = "FROM User as u WHERE (lower(u.name) = :name or lower(u.email) = :email) and u.password = :password";
        User result = null;

        try (Session session = sessionFactory.openSession()){
            Query<User> query = session.createQuery(hql);
            query.setParameter("name", nameOrEmail.toLowerCase());
            query.setParameter("email", nameOrEmail.toLowerCase());
            query.setParameter("password", password);
            result = query.uniqueResult();
            session.close();
        }catch (Exception e){
            logger.error("Failure to get the user by credential.", e.getMessage());
        }
        if (result != null) logger.debug(String.format("Got the user with Credential: %s and %s.", nameOrEmail, password));
        return result;
    }
}
