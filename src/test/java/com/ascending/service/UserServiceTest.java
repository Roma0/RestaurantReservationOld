package com.ascending.service;

import com.ascending.AppInitializer;
import com.ascending.model.Reservation;
import com.ascending.model.Restaurant;
import com.ascending.model.User;
import com.ascending.repository.ReservationDao;
import com.ascending.service.UserService;
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
import java.time.ZonedDateTime;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class UserServiceTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    //Todo
    @Autowired
    private UserService userService;
    private User user;
    private String userName = "Han";
    private String email = "hanwang@gmail.com";

    @Autowired
    private RestaurantService restaurantService;
    private Restaurant restaurant;
    private String restaurantName = "Agora Tysons2";
    private String restaurantAddress = "7911 Westpark Dr, McLean, VA 22102";
    private LocalTime openTime = LocalTime.of(10,0);
    private LocalTime closeTime = LocalTime.of(21,30);

    @Autowired
    private ReservationDao reservationDao;
    private Reservation reservation;
    private ZonedDateTime reservationTime = ZonedDateTime.now();
    private int numPersons = 2;

    @Before
    public void setUp(){
        logger.debug("Setting up before test ...");

        restaurant = new Restaurant(restaurantName, restaurantAddress, openTime, closeTime);
        restaurant = restaurantService.save(restaurant);
        user = new User(userName, email);
        user = userService.save(user);
        reservation = new Reservation(reservationTime, numPersons, restaurant, user);
        reservation = reservationDao.save(reservation);
        Assert.assertNotNull(reservation.getId());
    }

    @Test
    public void getReservationsOrReviewsByUserNameOrEmail(){
        String childrenType1 = "reservation";
//        String childrenType2 = "review";
        Map<String, Object> userReservations = userService.getReservationsOrReviewsByUserNameOrEmail(userName, childrenType1);
        User user = (User)userReservations.get("user");
//        logger.debug(String.format(">>>>>>>see user %s", user));
        Assert.assertEquals(userName, user.getName());
    }
}
