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
    private User user;
    private Restaurant restaurant;

    //Todo reset ReviewDaoTest without seeding data
    @Before
    public void setUp(){
        logger.debug("Setting up before test ...");
        restaurant = restaurantDao.getRestaurantById(1L);
        user = userDao.getUserByNameOrEmail("Han");
        review = new Review(restaurant, user);
        review = reviewDao.save(review);
        Assert.assertNotNull(review.getId());
    }

    @After
    public void tearDown(){
        logger.debug("Tearing down after test ...");
        if (review.getId() != null)Assert.assertTrue(reviewDao.deleteById(review.getId()));
    }

    //Todo Modify size() - 1
    @Test
    public void getReviews(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        List<Review> reviews = reviewDao.getReviews();
        Assert.assertTrue(reviews.size() >= 1);
        Assert.assertEquals(review.getId(), reviews.get(reviews.size() - 1).getId());
    }

    @Test
    public void getReviewById(){
//        logger.debug(">>>>>>>>>>>" +  reviewDao.getReviewById(1L).toString());
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
