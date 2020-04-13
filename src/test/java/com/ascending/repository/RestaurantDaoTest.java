package com.ascending.repository;

import com.ascending.AppInitializer;
import com.ascending.model.Reservation;
import com.ascending.model.Restaurant;
import com.ascending.model.Review;
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
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class RestaurantDaoTest {
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
    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private ReviewDao reviewDao;
    private User user;
    private ZonedDateTime reservedTime = ZonedDateTime.now();
    private int numPerson = 2;


    @Before
    public void setUp(){
        logger.debug("Setting up before the testing ...");
        user = new User("testUserName", "test@email.com");
        restaurant = new Restaurant(restaurantName, restaurantAddress,
                openTime, closeTime);
        restaurant = restaurantDao.save(restaurant);
        Assert.assertNotNull(restaurant.getId());
    }

    @After
    public void tearDown(){
        logger.debug("Tearing down after the testing ...");
        if(restaurant.getId() != null) Assert.assertTrue(restaurantDao.cascadeDeleteById(restaurant.getId()));
        if(user.getId() != null) Assert.assertTrue(userDao.deleteById(user.getId()));
    }

    @Test
    public void update(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        String phone = "1234567890";
        restaurant.setPhone(phone);
        Assert.assertEquals(phone, restaurantDao.update(restaurant).getPhone());
    }

    //Todo Modify size() - 1
    @Test
    public void getRestaurants(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        List<Restaurant> restaurants = restaurantDao.getRestaurants();
        int size = restaurants.size();
        Assert.assertEquals(restaurant.getId(), restaurants.get(size - 1).getId());
    }

    @Test
    public void getRestaurantById(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        Assert.assertEquals(restaurant.getId(), restaurantDao.getRestaurantById(restaurant.getId()).getId());
    }

    @Test
    public void getRestaurantsByAddress(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        Assert.assertTrue(restaurantDao.getRestaurantsByAddress(restaurant.getAddress()).size() > 0);
    }

    @Test
    public void getRestaurantByName(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        Assert.assertEquals(restaurant.getName(), restaurantDao.getRestaurantByName(restaurant.getName()).getName());
    }

    @Test
    public void cascadeDeleteById(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        user = userDao.save(user);
        Reservation reservation = new Reservation(reservedTime, numPerson, restaurant, user);
        reservation = reservationDao.save(reservation);
        Review review = new Review(restaurant,user);
        review = reviewDao.save(review);
        Assert.assertEquals(restaurant.getId(), reservation.getRestaurantId());
        Assert.assertEquals(restaurant.getId(), review.getRestaurantId());
        Assert.assertTrue(restaurantDao.cascadeDeleteById(restaurant.getId()));
        restaurant.setId(null);
        Assert.assertNull(reservationDao.getReservationById(reservation.getId()));
        Assert.assertNull(reviewDao.getReviewById(review.getId()));
    }

    @Test
    public void getRestaurantWithReservations(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        user = userDao.save(user);
        Reservation reservation1 = new Reservation(reservedTime, numPerson, restaurant, user);
        reservationDao.save(reservation1);
        Reservation reservation2 = new Reservation(reservedTime, numPerson, restaurant, user);
        reservationDao.save(reservation2);
        Set<Reservation> reservations = restaurantDao.getRestaurantWithReservations(restaurant.getId()).getReservations();

        //To assert reservation exist
        Assert.assertEquals(2, reservations.size());
        reservations.forEach(e -> Assert.assertEquals(restaurant.getId(), e.getRestaurantId()));
    }

    @Test
    public void getRestaurantWithReviews(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        user = userDao.save(user);
        Review review1 = new Review(restaurant, user);
        Review review2 = new Review(restaurant, user);
        reviewDao.save(review1);
        reviewDao.save(review2);
        Set<Review> reviews = restaurantDao.getRestaurantWithReviews(restaurant.getId()).getReviews();

        //To assert reservation exist
        Assert.assertEquals(2, reviews.size());
        reviews.forEach(e -> Assert.assertEquals(restaurant.getId(), e.getRestaurantId()));
    }

}
