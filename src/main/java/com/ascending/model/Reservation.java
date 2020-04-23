package com.ascending.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.*;
import java.util.Objects;


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

    @UpdateTimestamp
    @Column(name = "update_time", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime updateTime;

    @Column(name = "reserved_time", columnDefinition= "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime reservedTime;

    @Column(name = "num_persons")
    private int numPersons;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "reserved_status", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private ReservedStatus  reservedStatus = ReservedStatus.PENDING;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss a z", timezone = "UTC")
    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss a z", timezone = "UTC")
    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss a z", timezone = "UTC")
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

    @JsonProperty("restaurantId")
    public Long getRestaurantId() {
        return restaurant.getId();
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @JsonProperty("userId")
    public Long getUserId() {
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
        try {
            str = objectMapper.writeValueAsString(this);
        }
        catch (JsonProcessingException jpe){
            jpe.printStackTrace();
        }
        return str;
    }

    @Override
    public int hashCode(){
        return Objects.hash(id, restaurant.getId(), user.getId());
    }

    @Override
    public boolean equals(Object o){
        if (o == null || o.getClass() != getClass()) return false;
        if (o == this) return true;
        Reservation reservation = (Reservation) o;
        return reservation.id == id &&
                reservation.createdTime.equals(createdTime) &&
                Objects.equals(reservation.updateTime, updateTime) &&
                Objects.equals(reservation.reservedTime, reservedTime) &&
                reservation.numPersons == numPersons &&
                reservation.reservedStatus.equals(reservedStatus) &&
                reservation.restaurant.getId().equals(restaurant.getId()) &&
                reservation.user.getId().equals(user.getId());

    }
}
