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

        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            logger.debug(String.format("The user %s was insert into table.", user.toString()));
            return user;
        }
        catch (Exception e){
            if(transaction != null)transaction.rollback();
            logger.error("Failure to save user.", e.getMessage());
        }
        return null;
    }

    @Override
    public User update(User user) {
        Transaction transaction = null;

        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
            logger.debug(String.format("The user %s was updated", user.getName()));
            return user;
        }catch (Exception e){
            if(transaction != null)transaction.rollback();
            logger.error("Failure to update user.", e.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(User user) {
        Transaction transaction = null;
        boolean result = false;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            user = session.find(User.class, user.getId());
            session.delete(user);
            result = true;
            transaction.commit();
            session.close();
            logger.debug(String.format("A cascade deletion was completed to the user: %s.", user.getName()));
        } catch (Exception e) {
            if (transaction != null) transaction.commit();
            logger.error("Failure to delete user.", e.getMessage());
        }
        return result;
    }

    @Override
    public List<User> getUsers() {
        String hql = "FROM User";
        try(Session session = sessionFactory.openSession()){
            Query<User> query = session.createQuery(hql);
            return query.getResultList();
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public User getUserByEmail(String email) {
        String hql = "FROM User as u WHERE lower(u.email) = :email ";

        try (Session session = sessionFactory.openSession()){
            Query<User> query = session.createQuery(hql);
            query.setParameter("email", email.toLowerCase());
            logger.debug(String.format("Get the user with email: %s.", email));
            return query.uniqueResult();
        }catch (Exception e){
            logger.error(String.format("Failure to get the user by email: %s.", email), e.getMessage());
        }
        return null;
    }

    @Override
    public User getUserByCredential(String email, String password) {
        String hql = "FROM User as u WHERE lower(u.email) = :email and u.password = :password";

        try (Session session = sessionFactory.openSession()){
            Query<User> query = session.createQuery(hql);
            query.setParameter("email", email.toLowerCase());
            query.setParameter("password", password);
            logger.debug(String.format("Get the user with Credential: %s and %s.", email, password));
            return query.uniqueResult();
        }catch (Exception e){
            logger.error("Failure to get the user by credential.", e.getMessage());
        }
        return null;
    }
}
