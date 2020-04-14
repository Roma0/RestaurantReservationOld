package com.ascending.service;

import com.ascending.model.Reservation;
import com.ascending.model.Review;
import com.ascending.model.User;
import com.ascending.repository.ReservationDao;
import com.ascending.repository.ReviewDao;
import com.ascending.repository.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDao userDao;
    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private ReviewDao reviewDao;

    public User save(User user){
        return userDao.save(user);
    }

    public User update(User user){
        return userDao.update(user);
    }

    public boolean deleteById(Long id){
        return userDao.deleteById(id);
    }

    public List<User> getUsers(){
        return userDao.getUsers();
    }

    public User getUserByNameOrEmail(String nameOrEmail){
        return userDao.getUserByNameOrEmail(nameOrEmail);
    }

    public User getUserByCredential(String nameOrEmail, String password){
        return userDao.getUserByCredential(nameOrEmail, password);
    }

    public Map<String, Object> getReservationsOrReviewsByUserNameOrEmail(String nameOrEmail, String childrenType){
        Map<String, Object> result = new HashMap<>();
        User user = userDao.getUserByNameOrEmail(nameOrEmail);
        if(user != null) {
            result.put("user", user);
        }else {
            return null;
        }

        if (childrenType.toLowerCase() == "reservation"){
            List<Reservation> reservations = reservationDao.getReservationsByUserId(user.getId());
            result.put(childrenType.toLowerCase(), reservations);
            return result;
        }else if(childrenType.toLowerCase() == "review"){
            List<Review> reviews = reviewDao.getReviewsByUserId(user.getId());
            result.put(childrenType.toLowerCase(), reviews);
            return result;
        }else {
            logger.error(String.format("Invalid to get childrenType of %s, [reservation/review] only.",childrenType));
        }

        return null;
    }
}
