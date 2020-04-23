package com.ascending.service;

import com.ascending.model.Reservation;
import com.ascending.model.Restaurant;
import com.ascending.repository.RestaurantDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantDao restaurantDao;

    public Restaurant save(Restaurant restaurant){return restaurantDao.save(restaurant);}

    public Restaurant update(Restaurant restaurant){return restaurantDao.update(restaurant);}

    public boolean cascadeDeleteById(Long id){return restaurantDao.cascadeDeleteById(id);}

    public List<Restaurant> getRestaurants(){return restaurantDao.getRestaurants();}

    public Restaurant getRestaurantById(Long id){return restaurantDao.getRestaurantById(id);}

    public List<Restaurant> getRestaurantsByAddress(String address){return restaurantDao.getRestaurantsByAddress(address);}

    public Restaurant getRestaurantByName(String name){return restaurantDao.getRestaurantByName(name);}

    public Restaurant getRestaurantWithReservations(Long id){return restaurantDao.getRestaurantWithReservations(id);}

    public Restaurant getRestaurantWithReviews(Long id){return restaurantDao.getRestaurantWithReviews(id);}

    public Restaurant getRestaurantWithPendingReservationsById(Long id){
        Restaurant restaurant = restaurantDao.getRestaurantWithReservations(id);
        Set<Reservation> pendingReservations = restaurant.getReservations().stream()
                .filter(e -> e.getReservedStatus() == Reservation.ReservedStatus.PENDING).collect(Collectors.toSet());
        restaurant.setReservations(pendingReservations);
        return restaurant;
    }
}
