package com.ascending.repository;

import com.ascending.model.Reservation;
import com.ascending.model.Restaurant;

import java.util.List;

public interface RestaurantDao {
    Restaurant save(Restaurant restaurant);
    Restaurant update(Restaurant restaurant);
    boolean cascadeDeleteById(Long id);
    List<Restaurant> getRestaurants();
    Restaurant getRestaurantById(Long id);
    List<Restaurant> getRestaurantsByAddress(String address);
    Restaurant getRestaurantByName(String name);
    Restaurant getRestaurantWithReservations(Long id);
    Restaurant getRestaurantWithReviews(Long id);
}
