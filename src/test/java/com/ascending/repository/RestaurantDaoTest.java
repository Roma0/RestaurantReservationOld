package com.ascending.repository;

import com.ascending.AppInitializer;
import com.ascending.model.Reservation;
import com.ascending.model.Restaurant;
import com.ascending.model.Review;
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
    private Long id;

    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private ReviewDao reviewDao;
    private Reservation reservation;
    private Review review;


    @Before
    public void setUp(){
        logger.debug("Setting up before the testing ...");
        restaurant = new Restaurant("Agora Tysons2","7911 Westpark Dr, McLean, VA 22102",
                LocalTime.of(10,0),LocalTime.of(21,30));
        id = null;
        id = restaurantDao.save(restaurant).getId();
        Assert.assertNotNull(id);
    }

    @After
    public void tearDown(){
        logger.debug("Tearing down after the testing ...");
        if(id != null) Assert.assertTrue(restaurantDao.cascadeDeleteById(id));
    }

    @Test
    public void update(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        String phone = "1234567890";
        restaurant.setPhone(phone);
        Assert.assertEquals(phone, restaurantDao.update(restaurant).getPhone());
    }

    @Test
    public void getRestaurants(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        List<Restaurant> restaurants = restaurantDao.getRestaurants();
        int size = restaurants.size();
        Assert.assertEquals(id, restaurants.get(size - 1).getId());
    }

    @Test
    public void getRestaurantById(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        Assert.assertEquals(id, restaurantDao.getRestaurantById(id).getId());
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

//    //Todo test Restaurant with Children
//    @Test
//    public void cascadeDeleteById(){
//        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
//        reservation = new Reservation(ZonedDateTime.now(),)
//        Reservation reservation = new Reservation();
//    }

//    @Test
//    public void getRestaurantWithReservations(){
//        logger.debug("Testing RestaurantDao.getRestaurantWithReservations() ...");
//        Assert.assertEquals(id, restaurantDao.getRestaurantWithReservations(id).getId());
//        //To assert reservation exist
//        Restaurant r2 = restaurantDao.getRestaurantWithReservations(1L);
//        Set<Reservation> reservations = r2.getReservations();
//        Assert.assertNotNull(reservations.iterator().next().getId());
//    }
//
//    @Test
//    public void getRestaurantWithReviews(){
//        logger.debug("Testing RestaurantDao.getRestaurantsWithReviews() ...");
//        Assert.assertEquals(id, restaurantDao.getRestaurantWithReviews(id).getId());
//        //To assert reservation exist
//        Restaurant r2 = restaurantDao.getRestaurantWithReviews(1L);
//        Assert.assertTrue(r2.getReviews().size() > 0 );
//    }

}
