package com.ascending.repository;

import com.ascending.AppInitializer;
import com.ascending.model.Reservation;
import com.ascending.model.Restaurant;
import com.ascending.model.User;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZonedDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class ReservationDaoTest {
    @Rule
    public TestName testName = new TestName();
    public String className = this.getClass().getName().replaceAll("Test$", "");

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RestaurantDao restaurantDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ReservationDao reservationDao;

    private Restaurant restaurant;
    private User user;
    private Reservation reservation;
    private ZonedDateTime reservationTime = ZonedDateTime.now();
    private int numPersons = 2;

    //Todo reset ReviewDaoTest without seeding data
    @Before
    public void setUp(){
        logger.debug("Setting up before test ...");

        restaurant = restaurantDao.getRestaurantById(1L);
        user = userDao.getUserByNameOrEmail("Han");
        reservation = new Reservation(reservationTime, numPersons, restaurant, user);
        reservation = reservationDao.save(reservation);
        Assert.assertNotNull(reservation.getId());
    }

    @After
    public void tearDown(){
        logger.debug("Tearing down after test ...");
        if(reservation.getId() != null)Assert.assertTrue(reservationDao.deleteById(reservation.getId()));
    }

    @Test
    public void update(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        Reservation.ReservedStatus reservedStatus = Reservation.ReservedStatus.CONFIRMED;
        if(reservedStatus != reservation.getReservedStatus())reservation.setReservedStatus(reservedStatus);
        Assert.assertEquals(reservedStatus, reservationDao.update(reservation).getReservedStatus());
    }

    //Todo Modify Assert.assertEquals(reservation.getId(), reservations.get(lastIndex).getId());
    @Test
    public void getReservations(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        List<Reservation> reservations = reservationDao.getReservations();
        int lastIndex = reservations.size() - 1 ;
        Assert.assertTrue(reservations.size() > 0);
        Assert.assertEquals(reservation.getId(), reservations.get(lastIndex).getId());
    }

    @Test
    public void getReservationById(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        Assert.assertEquals(reservation.getId(), reservationDao.getReservationById(reservation.getId()).getId());
    }

    @Test
    public void getReservationsByUserId(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        List<Reservation> reservations = reservationDao.getReservationsByUserId(user.getId());
        Assert.assertTrue(reservations.size() > 0);
        reservations.forEach(e -> Assert.assertEquals(user.getId(),e.getUserId()));
    }
}
