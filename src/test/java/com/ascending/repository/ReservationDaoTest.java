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

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class ReservationDaoTest {
    @Rule
    public TestName testName = new TestName();
    public String className = this.getClass().getName().replaceAll("Test$", "");

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RestaurantDao restaurantDao;
    private Restaurant restaurant;
    private String restaurantName = "Agora Tysons2";
    private String restaurantAddress = "7911 Westpark Dr, McLean, VA 22102";
    private LocalTime openTime = LocalTime.of(10,0);
    private LocalTime closeTime = LocalTime.of(21,30);

    @Autowired
    private UserDao userDao;
    private User user;
    private String userName = "Han";
    private String email = "hanwang@gmail.com";

    @Autowired
    private ReservationDao reservationDao;
    private Reservation reservation;
    private ZonedDateTime reservationTime = ZonedDateTime.now();
    private int numPersons = 2;

    @Before
    public void setUp(){
        logger.debug("Setting up before test ...");

        restaurant = new Restaurant(restaurantName, restaurantAddress, openTime, closeTime);
        restaurant = restaurantDao.save(restaurant);
        user = new User(userName, email);
        user = userDao.save(user);
        reservation = new Reservation(reservationTime, numPersons, restaurant, user);
        reservation = reservationDao.save(reservation);
        Assert.assertNotNull(reservation.getId());
    }

    @After
    public void tearDown(){
        logger.debug("Tearing down after test ...");
        if(reservation.getId() != null)Assert.assertTrue(reservationDao.deleteById(reservation.getId()));
        if(user.getId() != null)Assert.assertTrue(userDao.deleteById(user.getId()));
        if(restaurant.getId() != null) Assert.assertTrue(restaurantDao.cascadeDeleteById(restaurant.getId()));
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
        Assert.assertTrue(reservations.size() > 0);
        Optional<Integer> lastId =  reservations.stream().map(r -> r.getId().intValue()).max(Comparator.naturalOrder());
        Assert.assertEquals(reservation.getId().intValue(), lastId.get().intValue());
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
