package com.ascending.service;

import com.ascending.AppInitializer;
import com.ascending.model.Reservation;
import com.ascending.model.Restaurant;
import com.ascending.model.Review;
import com.ascending.model.User;
import com.ascending.repository.ReservationDao;
import com.ascending.repository.ReviewDao;
import com.ascending.repository.UserDao;
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
import java.util.stream.Collectors;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class RestaurantServiceTest {
    @Rule
    public TestName testName = new TestName();
    private String className = this.getClass().getName().replaceAll("Test$", "");

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService restaurantService;
    private Restaurant restaurant;
    String name = "Agora Tysons2";
    String address = "7911 Westpark Dr, Mclean, VA 22102";
    @Autowired
    private UserDao userDao;
    private User user;
    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private ReviewDao reviewDao;

    @Before
    public void setUp(){
        logger.debug("Setting up before the testing ...");
        restaurant = new Restaurant(name, address,
                LocalTime.of(10, 0), LocalTime.of(21, 30));
        user = new User("Han", "hanwang@gmail.com");
        restaurant = restaurantService.save(restaurant);
        Assert.assertNotNull(restaurant.getId());
    }

    @After
    public void tearDown(){
        logger.debug("Tearing down after the testing ...");
        if(restaurant.getId() != null)Assert.assertTrue(restaurantService.cascadeDeleteById(restaurant.getId()));
        if(user.getId() != null)Assert.assertTrue(userDao.deleteById(user.getId()));
    }

    @Test
    public void update(){
        logger.debug("Testing {} for {} method.", className, testName.getMethodName());
        String phone = "1234567890";
        restaurant.setPhone(phone);
        Assert.assertEquals(phone, restaurantService.update(restaurant).getPhone());
    }

    @Test
    public void getRestaurants(){
        logger.debug("Testing {} for {} method.", className, testName.getMethodName());
        Assert.assertTrue(restaurantService.getRestaurants().size() > 0);
    }

    @Test
    public void getRestaurantById(){
        logger.debug("Testing {} for {} method.", className, testName.getMethodName());
        Assert.assertEquals(restaurant.getId(), restaurantService.getRestaurantById(restaurant.getId()).getId());
    }

    @Test
    public void getRestaurantsByAddress(){
        logger.debug("Testing {} for {} method.", className, testName.getMethodName());
        restaurantService.getRestaurantsByAddress(address)
                .forEach(e -> Assert.assertTrue(e.getAddress().equalsIgnoreCase(address)));
    }

    @Test
    public void getRestaurantByName(){
        logger.debug("Testing {} for {} method.", className, testName.getMethodName());
        Assert.assertEquals(name, restaurantService.getRestaurantByName(name).getName());
    }

    @Test
    public void getRestaurantWithReservations(){
        logger.debug("Testing {} for {} method.", className, testName);
        user = userDao.save(user);
        Reservation reservation = new Reservation(ZonedDateTime.now(), 3, restaurant, user);
        reservation = reservationDao.save(reservation);
        restaurant = restaurantService.getRestaurantWithReservations(restaurant.getId());
        Assert.assertTrue(restaurant.getReservations().size() > 0);
        Assert.assertTrue(restaurant.getReservations().contains(reservation));
    }

    @Test
    public void getRestaurantWithReviews(){
        logger.debug("Testing {} for {} method.", className, testName.getMethodName());
        user = userDao.save(user);
        Review review = new Review(restaurant, user);
        review = reviewDao.save(review);
        restaurant = restaurantService.getRestaurantWithReviews(restaurant.getId());
        Assert.assertTrue(restaurant.getReviews().size() > 0);
        Assert.assertEquals(review.getId(), restaurant.getReviews().stream().map(e -> e.getId())
                        .collect(Collectors.maxBy(Comparator.naturalOrder())).get());
    }

    @Test
    public void getRestaurantWithPendingReservationsById(){
        logger.debug("Test {} for {} method.", className, testName.getMethodName());
        user = userDao.save(user);

        Reservation reservation1 = new Reservation(ZonedDateTime.now(), 3, restaurant, user);
        Reservation reservation2 = new Reservation(ZonedDateTime.now(), 2, restaurant, user);
        reservation1.setReservedStatus(Reservation.ReservedStatus.CONFIRMED);

        reservationDao.save(reservation1);
        reservationDao.save(reservation2);

        restaurant = restaurantService.getRestaurantWithPendingReservationsById(restaurant.getId());
        Assert.assertTrue(restaurant.getReservations().size() == 1);
        Assert.assertTrue(restaurant.getReservations().stream()
                .allMatch(e -> e.getReservedStatus() == Reservation.ReservedStatus.PENDING));
    }
}
