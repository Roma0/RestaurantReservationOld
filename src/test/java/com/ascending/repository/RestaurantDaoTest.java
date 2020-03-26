package com.ascending.repository;

import com.ascending.AppInitializer;
import com.ascending.model.Restaurant;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class RestaurantDaoTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RestaurantDao restaurantDao;
    private Restaurant restaurant = new Restaurant("Agora Tysons2","7911 Westpark Dr, McLean, VA 22102",
            LocalTime.of(10,0),LocalTime.of(21,30));
    private Long id;


    @Before
    public void setUp(){
        logger.debug("Setting up before the testing ...");
        id = null;
        id = restaurantDao.save(restaurant).getId();
        Assert.assertNotNull(id);
    }

    @After
    public void tearDown(){
        logger.debug("Tearing down after the testing ...");
        Assert.assertTrue(restaurantDao.delete(restaurant));
    }

    @Test
    public void update(){
        logger.debug("Testing RestaurantDao.update() ...");
        String phone = "1234567890";
        restaurant.setPhone(phone);
        Assert.assertEquals(phone, restaurantDao.update(restaurant).getPhone());
    }

    @Test
    public void getRestaurants(){
        logger.debug("Testing RestaurantDao.getRestaurants() ...");
        List<Restaurant> restaurants = restaurantDao.getRestaurants();
        int size = restaurants.size();
        Assert.assertEquals(id, restaurants.get(size - 1).getId());
    }

    @Test
    public void getRestaurantById(){
        logger.debug("Testing RestaurantDao.getRestaurantById() ...");
        Assert.assertEquals(id, restaurantDao.getRestaurantById(id).getId());
    }

    @Test
    public void getRestaurantsByAddress(){
        logger.debug("Testing RestaurantDao.getRestaurantsByAddress() ...");
        Assert.assertTrue(restaurantDao.getRestaurantsByAddress(restaurant.getAddress()).size() > 0);
    }

    @Test
    public void getRestaurantsByName(){
        logger.debug("Testing RestaurantDao.getRestaurantsByName() ...");
        Assert.assertEquals(restaurant.getName(), restaurantDao.getRestaurantByName(restaurant.getName()).getName());
    }

    @Test
    public void getRestaurantWithReservations(){
        logger.debug("Testing RestaurantDao.getRestaurantWithReservations() ...");
        Assert.assertEquals(id, restaurantDao.getRestaurantWithReservations(id).getId());
        //To assert reservation exist
        Restaurant r2 = restaurantDao.getRestaurantWithReservations(1L);
        Assert.assertTrue(r2.getReservations().size() > 0);
    }

    @Test
    public void getRestaurantWithReviews(){
        logger.debug("Testing RestaurantDao.getRestaurantsWithReviews() ...");
        Assert.assertEquals(id, restaurantDao.getRestaurantWithReviews(id).getId());
        //To assert reservation exist
        Restaurant r2 = restaurantDao.getRestaurantWithReviews(1L);
        Assert.assertTrue(r2.getReviews().size() > 0 );
    }

}
