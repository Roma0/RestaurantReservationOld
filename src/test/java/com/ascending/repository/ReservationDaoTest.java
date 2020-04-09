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
    private Reservation reservation;
    private Long id;

    @Before
    public void setUp(){
        logger.debug("Setting up before test ...");
        id = null;
        ZonedDateTime reservationTime = ZonedDateTime.now();
        int numPersons = 2;
        Restaurant restaurant = restaurantDao.getRestaurantById(1L);
        User user = userDao.getUserByNameOrEmail("Han");
        reservation = new Reservation(reservationTime, numPersons, restaurant, user);
        reservation = reservationDao.save(reservation);
        id = reservation.getId();
        Assert.assertNotNull(id);
    }

    @After
    public void tearDown(){
        logger.debug("Tearing down after test ...");
        if(id != null)Assert.assertTrue(reservationDao.deleteById(id));
    }

    @Test
    public void update(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        Reservation.ReservedStatus reservedStatus = Reservation.ReservedStatus.CONFIRMED;
        if(reservedStatus != reservation.getReservedStatus())reservation.setReservedStatus(reservedStatus);
        Assert.assertEquals(reservedStatus, reservationDao.update(reservation).getReservedStatus());
    }

    @Test
    public void getReservations(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        List<Reservation> reservations = reservationDao.getReservations();
        int lastIndex = reservations.size() - 1 ;
        Assert.assertEquals(id, reservations.get(lastIndex).getId());
    }

    @Test
    public void getReservationById(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        Assert.assertEquals(id, reservationDao.getReservationById(id).getId());
    }
}
