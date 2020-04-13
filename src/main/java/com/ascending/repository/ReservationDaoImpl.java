package com.ascending.repository;

import com.ascending.model.Reservation;
import com.ascending.model.Restaurant;
import com.ascending.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class ReservationDaoImpl implements ReservationDao{
    private Logger logger = LoggerFactory.getLogger(getClass());
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public Reservation save(Reservation reservation) {
        Transaction transaction = null;
        Reservation result = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.save(reservation);
            result = reservation;
            transaction.commit();
            session.close();
        }
        catch (Exception e){
            if(transaction != null)transaction.rollback();
            logger.error("Failure to save reservation.", e.getMessage());
        }
        if (result != null)logger.debug(String.format("The reservation %s was inserted into database.", result));
        return result;
    }

    @Override
    public Reservation update(Reservation reservation) {
        Transaction transaction = null;
        Reservation result = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            reservation.setUpdateTime(ZonedDateTime.now(ZoneId.of("UTC")));
            session.saveOrUpdate(reservation);
            result = reservation;
            transaction.commit();
            session.close();
        }catch (Exception e){
            if(transaction != null)transaction.rollback();
            logger.error("Failure to update reservation.", e.getMessage());
        }
        if(result != null)logger.debug(String.format("The reservation %s was updated in database.", result));
        return result;
    }

    @Override
    public boolean deleteById(Long id) {
        Transaction transaction = null;
        int result = 0;
        String hql = "DELETE FROM Reservation as r WHERE r.id = :id";

        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            Query<Reservation> query = session.createQuery(hql);
            query.setParameter("id", id);
            result = query.executeUpdate();
            transaction.commit();
            session.close();
        }catch (Exception e){
            if(transaction != null)transaction.rollback();
            logger.error("Failure to delete reservation.", e.getMessage());
        }
        if(result >= 1){
            logger.debug(String.format("Deleted the reservation by id=%s", id));
            return true;
        }
        return false;
    }

    @Override
    public List<Reservation> getReservations() {
        String hql = "FROM Reservation";
        List<Reservation> results = null;

        try (Session session = sessionFactory.openSession()){
            Query<Reservation> query = session.createQuery(hql);
            results = query.getResultList();
            session.close();
        }catch (Exception e){
            logger.error("Failure to get all reservations.", e.getMessage());
        }
        if (results != null)logger.debug(String.format("Got all %s reservations.", results.size()));
        return results;
    }

    @Override
    public Reservation getReservationById(Long id) {
        String hql = "FROM Reservation as r WHERE r.id = :id";
        Reservation result = null;

        try (Session session = sessionFactory.openSession()){
            Query<Reservation> query = session.createQuery(hql);
            query.setParameter("id", id);
            result = query.uniqueResult();
            session.close();
        }catch (Exception e){
            logger.error("Failure to get reservation.", e.getMessage());
        }
        if(result != null)logger.debug(String.format("Got reservation %s by id=%s.", result, id));
        return result;
    }

    @Override
    public List<Reservation> getReservationsByUserId(Long id) {
        String hql = "FROM Reservation AS r WHERE r.user.id = :id";
        List<Reservation> results = null;

        try (Session session = sessionFactory.openSession()){
            results = session.createQuery(hql).setParameter("id", id).getResultList();
            session.close();
        }catch (Exception e){
            logger.error("Failure to get reservations by userId.", e.getMessage());
        }
        if(results != null)logger.debug(String.format("Got %s reservations by userId=%s.", results.size(), id));
        return results;
    }
}
