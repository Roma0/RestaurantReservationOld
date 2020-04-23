package com.ascending.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.*;

@Entity
@Table(name = "Reviews")
public class Review {

    public Review(){};

    //For insert
    public Review(Restaurant restaurant, User user){
        this.restaurant = restaurant;
        this.user = user;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @CreationTimestamp
    @Column(name = "created_time", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime createdTime;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss a z", timezone = "UTC")
    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @JsonProperty("restaurantId")
    public Long getRestaurantId() {
        return restaurant.getId();
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @JsonProperty("userId")
    public Long getUserID() {
        return user.getId();
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String str = null;
        try{
            str = objectMapper.writeValueAsString(this);
        }catch (JsonProcessingException jpe){
            jpe.printStackTrace();
        }
        return str;
    }

}
