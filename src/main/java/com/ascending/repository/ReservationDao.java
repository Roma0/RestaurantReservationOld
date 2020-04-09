package com.ascending.repository;

import com.ascending.model.Reservation;

import java.util.List;

public interface ReservationDao {
    Reservation save(Reservation reservation);
    Reservation update(Reservation reservation);
    boolean deleteById(Long id);
    List<Reservation> getReservations();
    Reservation getReservationById(Long id);
}
