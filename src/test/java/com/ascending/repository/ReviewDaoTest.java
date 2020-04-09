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
    @Autowired
    private UserDao userDao;

    private Review review;
    private Long id;
    private Restaurant restaurant;

    @Before
    public void setUp(){
        logger.debug("Setting up before test ...");
        id = null;
        restaurant = restaurantDao.getRestaurantById(1L);
        User user = userDao.getUserByNameOrEmail("Han");
        review = new Review(restaurant, user);
        review = reviewDao.save(review);
        id = review.getId();
        Assert.assertNotNull(id);
    }

    @After
    public void tearDown(){
        logger.debug("Tearing down after test ...");
        if (id != null)Assert.assertTrue(reviewDao.deleteById(id));
    }

    @Test
    public void getReviews(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        List<Review> reviews = reviewDao.getReviews();
        Assert.assertTrue(reviews.size() >= 1);
        Assert.assertEquals(id, reviews.get(reviews.size() - 1).getId());
    }

    @Test
    public void getReviewById(){
//        logger.debug(">>>>>>>>>>>" +  reviewDao.getReviewById(1L).toString());
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        Assert.assertEquals(id, reviewDao.getReviewById(id).getId());
    }

    @Test
    public void getReviewsByRestaurantId(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        List<Review> reviews = reviewDao.getReviewsByRestaurantId(restaurant.getId());
        Assert.assertTrue(reviews.size() >= 1);
        Assert.assertEquals(id, reviews.get(reviews.size() - 1).getId());
    }
}
