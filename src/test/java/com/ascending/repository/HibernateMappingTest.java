package com.ascending.repository;

import com.ascending.AppInitializer;
import com.ascending.model.Reservation;
import com.ascending.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class HibernateMappingTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void mappingRestaurantTest(){
        logger.debug("Do the Restaurants table mapping...");
        String hql = "FROM Restaurant";
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Reservation> query = session.createQuery(hql);
            Assert.assertTrue(query.getResultList().size() > 0);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    @Test
    public void mappingUserTest(){
        logger.debug("Do the Users table mapping...");
        String hql = "FROM User";
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Reservation> query = session.createQuery(hql);
            Assert.assertTrue(query.getResultList().size() > 0);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    @Test
    public void mappingReservationTest(){
        logger.debug("Do the Reservations table mapping...");
        String hql = "FROM Reservation";
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Reservation> query = session.createQuery(hql);
            Assert.assertTrue(query.getResultList().size() > 0);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    @Test
    public void mappingReviewTest(){
        logger.debug("Do the Reviews table mapping...");
        String hql = "FROM Review";
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Reservation> query = session.createQuery(hql);
            Assert.assertTrue(query.getResultList().size() > 0);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

}
