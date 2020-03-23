package com.ascending.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.*;


@Entity
@Table(name = "reservations")
public class Reservation {

    public Reservation(){}

    //for insert
        public Reservation(ZonedDateTime reservedTime, int numPersons, Restaurant restaurant, User user){
        this.reservedTime = reservedTime;
        this.numPersons = numPersons;
        this.restaurant = restaurant;
        this.user = user;
    }

    public enum ReservedStatus{ PENDING, CONFIRMED, CANCELED; }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    @Column(name = "created_time", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime createdTime;

    //To add UpdateTimestamp
    @UpdateTimestamp
    @Column(name = "update_time", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime updateTime;

    @Column(name = "reserved_time", columnDefinition= "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime reservedTime;

    @Column(name = "num_persons")
    private int numPersons;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "reserved_status")
    private ReservedStatus  reservedStatus;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public ZonedDateTime getReservedTime() {
        return reservedTime;
    }

    public void setReservedTime(ZonedDateTime reservedTime) {
        this.reservedTime = reservedTime;
    }

    public int getNumPersons() {
        return numPersons;
    }

    public void setNumPersons(int numPersons) {
        this.numPersons = numPersons;
    }

    public ReservedStatus getReservedStatus() {
        return reservedStatus;
    }

    public void setReservedStatus(ReservedStatus reservedStatus) {
        this.reservedStatus = reservedStatus;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString(){
        ObjectMapper objectMapper = new ObjectMapper();
        String str = null;
        try {
            str = objectMapper.writeValueAsString(this);
        }
        catch (JsonProcessingException jpe){
            jpe.printStackTrace();
        }
        return str;
    }

}
