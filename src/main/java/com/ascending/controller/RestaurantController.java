package com.ascending.controller;

import com.ascending.model.Restaurant;
import com.ascending.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.soap.Addressing;
import java.util.List;

@RestController
@RequestMapping(value = {"/rests", "restaurants"})
public class RestaurantController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Restaurant updateRestaurantAddressByName(String name, String address){
        Restaurant restaurant = restaurantService.getRestaurantByName(name);
        restaurant.setAddress(address);
        return restaurantService.update(restaurant);
    }

    @Cacheable(value = "rests")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Restaurant getRestaurantById(@PathVariable Long id,
                                        @RequestParam(name = "withChildren", required = false) String withChildren ){

        if(withChildren == null) {
            return restaurantService.getRestaurantById(id);
        }else {
            if(withChildren.equalsIgnoreCase("reservations") ) {
                return restaurantService.getRestaurantWithReservations(id);
            }else if(withChildren.equalsIgnoreCase("reviews")) {
                return restaurantService.getRestaurantWithReviews(id);
            }else {
                return null;
            }
        }
    }
}
