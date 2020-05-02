package com.ascending.repository;

import com.ascending.model.Restaurant;
import com.ascending.model.Review;
import com.ascending.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewDaoImpl implements ReviewDao {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Review save(Review review) {
        Transaction transaction = null;
        Review result = null;

        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.save(review);
            result = review;
            transaction.commit();
            session.close();
        }catch (Exception e){
            logger.error("Failure to save review. {}", e.getMessage());
            if(transaction != null)transaction.rollback();
        }finally {
            if (result != null)logger.debug(String.format("The review %s was inserted into database.", result));
            return result;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        Transaction transaction = null;
        String hql = "DELETE FROM Review AS r WHERE r.id = :id";
        int result = 0;

        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            result = session.createQuery(hql).setParameter("id", id).executeUpdate();
            transaction.commit();
            session.close();
        }catch (Exception e){
            logger.error("Failure to delete review. {}", e.getMessage());
            if (transaction != null)transaction.rollback();
        }finally {
            if(result >= 1){
                logger.debug(String.format("Deleted the review by id=%s.", id));
                return true;
            }
            return false;
        }
    }

    @Override
    public List<Review> getReviews() {
        String hql = "FROM Review";
        List<Review> results = null;

        try (Session session = sessionFactory.openSession()){
            Query<Review> query = session.createQuery(hql);
            results = query.getResultList();
            session.close();
        }catch (Exception e){
            logger.error("Failure to get reviews. {}", e.getMessage());
        }
        if(results != null) logger.debug(String.format("Got all %s reviews.", results.size()));
        return results;
    }

    @Override
    public Review getReviewById(Long id) {
        String hql = "FROM Review AS r WHERE r.id = :id";
        Review result = null;

        try (Session session = sessionFactory.openSession()){
            result = (Review) session.createQuery(hql).setParameter("id", id).uniqueResult();
            session.close();
        }catch (Exception e){
            logger.error("Failure to get review. {}", e.getMessage());
        }
        if(result != null) logger.debug(String.format("Got the review %s by id=%s.", result, id));
        return result;
    }

//    @Override
//    public List<Review> getReviewsByRestaurantId(Long id) {
//        String hql = "FROM Review AS re WHERE re.restaurant.id = :id";
//        List<Review> results = null;
//
//        try (Session session = sessionFactory.openSession()){
//            results = (List<Review>) session.createQuery(hql).setParameter("id", id).getResultList();
//            session.close();
//        }catch (Exception e){
//            logger.error("Failure to get review by restaurant.", e.getMessage());
//        }
//        if(results != null)logger.debug(String.format("Got %s reviews by restaurant.id=%s.", results.size(), id));
//        return results;
//    }

    @Override
    public List<Review> getReviewsByUserId(Long id) {
        String hql = "FROM Review AS r WHERE r.user.id = :id";
        List<Review> results = null;

        try (Session session = sessionFactory.openSession()){
            results = session.createQuery(hql).setParameter("id", id).getResultList();
            session.close();
        }catch (Exception e){
            logger.error("Failure to get reviews by userID. {}", e.getMessage());
        }

        if(results != null)logger.debug(String.format("Got %s reviews by userId=%s.", results.size(), id));
        return results;
    }
}
