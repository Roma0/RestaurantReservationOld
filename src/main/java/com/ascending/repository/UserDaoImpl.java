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
//            session.close();
        }
        catch (Exception e){
            logger.error("Failure to save user. {}", e.getMessage());
            if(transaction != null)transaction.rollback();
        } finally {
            if(result != null)logger.debug(String.format("The user %s was inserted into table.", result));
            return result;
        }
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
            logger.error("Failure to update user. {}", e.getMessage());
            if(transaction != null)transaction.rollback();
        }finally {
            if(result != null)logger.debug(String.format("The user %s was updated in database.", result));
            return result;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        Transaction transaction = null;
        String hql1 = "UPDATE Reservation AS r SET r.user.id = NULL WHERE r.user.id = :id";
        String hql2 = "UPDATE Review AS r SET r.user.id = NULL WHERE r.user.id = :id";
        String hql3 = "DELETE User AS u WHERE u.id = :id";
        int result = 0;
        int updateReservations = 0;
        int updateReviews = 0;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            updateReservations = session.createQuery(hql1).setParameter("id", id).executeUpdate();
            updateReviews = session.createQuery(hql2).setParameter("id", id).executeUpdate();
            Query<User> query = session.createQuery(hql3);
            query.setParameter("id", id);
            result = query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            logger.error("Failure to delete user. {}", e.getMessage());
            if (transaction != null) transaction.rollback();
        }finally {
            if (result >= 1){
                logger.debug(String.format("Deleted the user by id=%s. Updated %s reservations and %s reviews.",
                        id, updateReservations, updateReviews));
                return true;
            }
            return false;
        }
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
            logger.error("Failure to get users. {}", e.getMessage());
        }

        if(results != null)logger.debug(String.format("Got all %s users.", results.size()));
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
            logger.error("Failure to get the user by nameOrEmail. {}", e.getMessage());
        }
        if (result != null) logger.debug(String.format("Got the user %s by nameOrEmail=%s.", result, nameOrEmail));
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
            logger.error("Failure to get the user by credential. {}", e.getMessage());
        }
        if (result != null) {
            logger.debug(
                    String.format("Got the user %s with credential: nameOrEmail=%s and password=%s.",
                            result, nameOrEmail, password)
            );
        }
        return result;
    }
}
