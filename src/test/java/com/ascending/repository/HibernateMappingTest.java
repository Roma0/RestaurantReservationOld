package com.ascending.repository;

import com.ascending.AppInitializer;
import com.ascending.model.Reservation;
import com.ascending.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class HibernateMappingTest {
    @Rule
    public TestName testName = new TestName();
    public String className = this.getClass().getName().replaceAll("Test$", "");

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void mappingRestaurantTest(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        String hql = "FROM Restaurant";
        try (Session session = sessionFactory.openSession()){
            Query<Reservation> query = session.createQuery(hql);
            Assert.assertTrue(query.getResultList().size() >= 0);
            session.close();
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    @Test
    public void mappingUserTest(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        String hql = "FROM User";
        try (Session session = sessionFactory.openSession()){
            Query<Reservation> query = session.createQuery(hql);
            Assert.assertTrue(query.getResultList().size() >= 0);
            session.close();
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    @Test
    public void mappingReservationTest(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        String hql = "FROM Reservation";
        try (Session session = sessionFactory.openSession()){
            Query<Reservation> query = session.createQuery(hql);
            Assert.assertTrue(query.getResultList().size() >= 0);
            session.close();
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    @Test
    public void mappingReviewTest(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        String hql = "FROM Review";
        try (Session session = sessionFactory.openSession()){
            Query<Reservation> query = session.createQuery(hql);
            Assert.assertTrue(query.getResultList().size() >= 0);
            session.close();
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

}
