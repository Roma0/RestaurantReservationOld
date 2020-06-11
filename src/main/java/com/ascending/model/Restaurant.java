package com.ascending.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "restaurants")
public class Restaurant implements Serializable{

    public Restaurant(){}

    //for Insert
    public Restaurant(String name, String address, LocalTime openTime, LocalTime closeTime){
        this.name = name;
        this.address = address;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "open_time")
    private LocalTime openTime;

    @Column(name = "close_time")
    private LocalTime closeTime;

    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Reservation> reservations;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Review> reviews;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss a")
    public LocalTime getOpenTime() { return openTime; }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss a")
    public LocalTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Reservation> getReservations() {
        try {
            int size = reservations.size();
        }
        catch (Exception e){
            return null;
        }
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Set<Review> getReviews() {
        try {
            int size = reviews.size();
        }catch (Exception e){
            return null;
        }
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
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
}
