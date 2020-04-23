package com.ascending.repository;

import com.ascending.model.Restaurant;
import com.ascending.util.HibernateUtil;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class RestaurantDaoImpl implements RestaurantDao {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public Restaurant save(Restaurant restaurant) {
        Transaction transaction = null;
        Restaurant result = null;

        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.save(restaurant);
            transaction.commit();
            result = restaurant;
            session.close();
        }
        catch (Exception e){
            logger.error("Failure to save restaurant. {}", e.getMessage());
            if(transaction != null)transaction.rollback();
        }
        finally {
            if (result != null) logger.debug(String.format("Inserted restaurant %s.", result.toString()));
            return result;
        }

    }

    @Override
    public Restaurant update(Restaurant restaurant) {
        Transaction transaction = null;
        Restaurant result = null;

        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.saveOrUpdate(restaurant);
            transaction.commit();
            result = restaurant;
            session.close();
        }
        catch (Exception e){
            logger.error("Failure to update restaurant. {}", e.getMessage());
            if(transaction != null)transaction.rollback();
        }
        finally {
            if (result != null) logger.debug(String.format("Updated restaurant %s.", result));
            return result;
        }

    }

    @Override
    public boolean cascadeDeleteById(Long id) {
        Transaction transaction = null;
        boolean result = false;

        try (Session session = sessionFactory.openSession()){
            Serializable serId = id;
            Restaurant restaurant = session.load(Restaurant.class, serId);
            if (restaurant != null) {
                transaction = session.beginTransaction();
                session.delete(restaurant);
                transaction.commit();
                result = true;
            }
            session.close();
        }
        catch (Exception e){
            if(transaction != null)transaction.rollback();
            logger.error("Failure to delete restaurant. {}", e.getMessage());
        }

        if(result) logger.debug(String.format("Completed cascade deletion from table: restaurants, reservations, reviews by restaurant.id=%s.",
                id));
        return result;
    }

    @Override
    public List<Restaurant> getRestaurants() {
        String hql = "FROM Restaurant";
        List<Restaurant> results = null;

        try(Session session = sessionFactory.openSession()){
            Query<Restaurant> query = session.createQuery(hql);
            results = query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
            session.close();
        }
        catch (Exception e){
            logger.error("Failure to get all restaurants. {}", e.getMessage());
        }

        if (results != null)logger.debug(String.format("Got %s restaurants.", results.size()));
        return results;
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        Restaurant result = null;

        try(Session session = sessionFactory.openSession()){
            result = session.find(Restaurant.class, id);
            session.close();
        }catch (Exception e){
            logger.error("Failure to find restaurant. {}", e.getMessage());
        }

        if (result != null) logger.debug(String.format("Got %s restaurant by id=%s.", result, id));
        return result;
    }

    @Override
    public List<Restaurant> getRestaurantsByAddress(String address) {
        String hql = "FROM Restaurant as r WHERE trim(lower(r.address)) = :address";
        List<Restaurant> results = null;

        try (Session session = sessionFactory.openSession()){
            Query<Restaurant> query = session.createQuery(hql);
            query.setParameter("address", address.trim().toLowerCase());
            results = query.getResultList();
            session.close();
        }catch (Exception e){
            logger.error("Failure to find restaurant. {}", e.getMessage());
        }

        if(results != null)logger.debug(String.format("Got %s reservations by address=%s.", results.size(), address));
        return results;
    }

    @Override
    public Restaurant getRestaurantByName(String name) {
        String hql = "FROM Restaurant as r WHERE trim(lower(r.name)) = :name ";
        Restaurant result = null;

        try (Session session = sessionFactory.openSession()){
            Query<Restaurant> query = session.createQuery(hql);
            query.setParameter("name", name.trim().toLowerCase());
            result = query.uniqueResult();
            session.close();
        }catch (Exception e){
            logger.error("Failure to find restaurant.{}", e.getMessage());
        }

        if(result != null)logger.debug(String.format("Got restaurant %s by name=%s.", result, name));
        return result;
    }

    @Override
    public Restaurant getRestaurantWithReservations(Long id) {
        String hql = "FROM Restaurant as r LEFT JOIN FETCH r.reservations WHERE r.id = :id";
        Restaurant result = null;

        try (Session session = sessionFactory.openSession()){
            Query<Restaurant> query = session.createQuery(hql);
            query.setParameter("id", id);
            result = query.uniqueResult();
            session.close();
        }catch (Exception e){
            logger.error("Failure to get restaurant with children. {}", e.getMessage());
        }

        if(result != null)logger.debug(String.format("Got restaurant with reservations %s by restaurant.id=%s", result, id));
        return result;
    }

    @Override
    public Restaurant getRestaurantWithReviews(Long id) {
        String hql = "FROM Restaurant as r LEFT JOIN FETCH r.reviews WHERE r.id = :id";
        Restaurant result = null;

        try (Session session = sessionFactory.openSession()){
            Query<Restaurant> query = session.createQuery(hql);
            query.setParameter("id", id);
            result = query.uniqueResult();
            session.close();
        }catch (Exception e){
            logger.error(String.format("Failure to get restaurant with id: %s and it's reviews. %s", id, e.getMessage()));
        }

        if(result != null)logger.debug(String.format("Got restaurant with reviews %s by restaurant.id=%s", result, id));
        return result;
    }
}
