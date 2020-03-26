package com.ascending.repository;

import com.ascending.model.Restaurant;
import com.ascending.util.HibernateUtil;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RestaurantDaoImpl implements RestaurantDao {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public Restaurant save(Restaurant restaurant) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.save(restaurant);
            transaction.commit();
            logger.debug(String.format("The restaurant %s was insert into table.", restaurant.toString()));
            return restaurant;
        }
        catch (Exception e){
            if(transaction != null)transaction.rollback();
            logger.error("Failure to save restaurant.", e.getMessage());
        }

        return null;
    }

    @Override
    public Restaurant update(Restaurant restaurant) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.saveOrUpdate(restaurant);
            transaction.commit();
            logger.debug(String.format("The restaurant %s was saved or updated", restaurant.getName()));
            return restaurant;
        }catch (Exception e){
            if(transaction != null)transaction.rollback();
            logger.error("Failure to update restaurant.", e.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(Restaurant restaurant) {
        Transaction transaction = null;
        boolean result = false;

        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            restaurant = session.find(Restaurant.class, restaurant.getId());
            session.delete(restaurant);
            transaction.commit();
            result = true;
            logger.debug(String.format("A cascade deletion was completed to the restaurant with ID: %s.",
                    restaurant.getId()));
            session.close();
        }catch (Exception e){
            if(transaction != null)transaction.commit();
            logger.error("Failure to delete restaurant.", e.getMessage());
        }
        return result;
    }

    @Override
    public List<Restaurant> getRestaurants() {
        String hql = "FROM Restaurant";

        try(Session session = sessionFactory.openSession()){
            Query<Restaurant> query = session.createQuery(hql);
            logger.debug("Get all restaurants");
            return query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        Restaurant result = null;
        try(Session session = sessionFactory.openSession()){
            result = session.find(Restaurant.class, id);
            logger.debug(String.format("Got restaurant with id: %s from database.", id));
        }catch (Exception e){
            logger.error(String.format("Failure to find restaurant with id: %s.", id), e.getMessage());
        }
        return result;
    }

    @Override
    public List<Restaurant> getRestaurantsByAddress(String address) {
        String hql = "FROM Restaurant as r WHERE trim(lower(r.address)) = :address";

        try (Session session = sessionFactory.openSession()){
            Query<Restaurant> query = session.createQuery(hql);
            query.setParameter("address", address.trim().toLowerCase());
            return query.getResultList();
        }catch (Exception e){
            logger.error(String.format("Failure to find restaurant at the address: %s.", address), e.getMessage());
        }

        return null;
    }

    @Override
    public Restaurant getRestaurantByName(String name) {
        String hql = "FROM Restaurant as r WHERE trim(lower(r.name)) = :name ";

        try (Session session = sessionFactory.openSession()){
            Query<Restaurant> query = session.createQuery(hql);
            query.setParameter("name", name.trim().toLowerCase());
            return query.uniqueResult();
        }catch (Exception e){
            logger.error(String.format("Failure to find restaurant with name of %s.", name), e.getMessage());
        }

        return null;
    }

    @Override
    public Restaurant getRestaurantWithReservations(Long id) {
        String hql = "FROM Restaurant as r LEFT JOIN FETCH r.reservations WHERE r.id = :id";
        try (Session session = sessionFactory.openSession()){
            Query<Restaurant> query = session.createQuery(hql);
            query.setParameter("id", id);
            return query.uniqueResult();
        }catch (Exception e){
            logger.error(String.format("Failure to get restaurant with id: %s and it's reservations.", id), e.getMessage());
        }
        return null;
    }

    @Override
    public Restaurant getRestaurantWithReviews(Long id) {
        String hql = "FROM Restaurant as r LEFT JOIN FETCH r.reviews WHERE r.id = :id";

        try (Session session = sessionFactory.openSession()){
            Query<Restaurant> query = session.createQuery(hql);
            query.setParameter("id", id);
            return query.uniqueResult();
        }catch (Exception e){
            logger.error(String.format("Failure to get restaurant with id: %s and it's reviews.", id), e.getMessage());
        }
        return null;
    }
}
