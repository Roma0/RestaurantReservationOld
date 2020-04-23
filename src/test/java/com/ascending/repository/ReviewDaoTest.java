package com.ascending.repository;

import com.ascending.AppInitializer;
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
import java.util.Comparator;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class ReviewDaoTest {
    @Rule
    public TestName testName = new TestName();
    public String className = this.getClass().getName().replaceAll("Test$", "");

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ReviewDao reviewDao;
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

    private Review review;

    @Before
    public void setUp(){
        logger.debug("Setting up before test ...");
        restaurant = new Restaurant(restaurantName, restaurantAddress, openTime, closeTime);
        restaurant = restaurantDao.save(restaurant);
        user = new User(userName, email);
        user = userDao.save(user);
        review = new Review(restaurant, user);
        review = reviewDao.save(review);
        Assert.assertNotNull(review.getId());
    }

    @After
    public void tearDown(){
        logger.debug("Tearing down after test ...");
        if (review.getId() != null)Assert.assertTrue(reviewDao.deleteById(review.getId()));
        if (user.getId() != null) Assert.assertTrue(userDao.deleteById(user.getId()));
        if (restaurant.getId() != null) Assert.assertTrue(restaurantDao.cascadeDeleteById(restaurant.getId()));
    }

    @Test
    public void getReviews(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        List<Review> reviews = reviewDao.getReviews();
        Assert.assertTrue(reviews.size() >= 1);
        Assert.assertEquals(review.getId(), reviews.stream().map(Review::getId).max(Comparator.naturalOrder()).get());
    }

    @Test
    public void getReviewById(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        Assert.assertEquals(review.getId(), reviewDao.getReviewById(review.getId()).getId());
    }

//    @Test
//    public void getReviewsByRestaurantId(){
//        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
//        List<Review> reviews = reviewDao.getReviewsByRestaurantId(restaurant.getId());
//        Assert.assertTrue(reviews.size() >= 1);
//        Assert.assertEquals(id, reviews.get(reviews.size() - 1).getId());
//    }

    @Test
    public void getReviewsByUserId(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        List<Review> reviews = reviewDao.getReviewsByUserId(user.getId());
        Assert.assertTrue(reviews.size() > 0);
        reviews.forEach(e -> Assert.assertEquals(user.getId(),e.getUserID()));
    }
}
