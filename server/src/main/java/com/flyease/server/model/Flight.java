package com.flyease.server.model;

import java.sql.Date;
import java.sql.Time;

public class Flight {
    int flightId;
    Date flightDate;
    Time flightTime;
    double flightPrice;
    int flightTotalSeats;
    int flightTotalPassengers;

    public Flight() {
        this.flightId = -1;
        this.flightDate = Date.valueOf("0000-00-00");
        this.flightTime = Time.valueOf("00:00:00");
        this.flightPrice = 100.00;
        this.flightTotalSeats = 50;
        this.flightTotalPassengers = 0;
    }

    public Flight(Date flightDate, Time flightTime, double flightPrice, int flightTotalSeats, int flightTotalPassengers) {
        this.flightId = -1;
        this.flightDate = flightDate;
        this.flightTime = flightTime;
        this.flightPrice = flightPrice;
        this.flightTotalSeats = flightTotalSeats;
        this.flightTotalPassengers = flightTotalPassengers;
    }

    public Flight(int flightId, Date flightDate, Time flightTime, double flightPrice, int flightTotalSeats, int flightTotalPassengers) {
        this.flightId = flightId;
        this.flightDate = flightDate;
        this.flightTime = flightTime;
        this.flightPrice = flightPrice;
        this.flightTotalSeats = flightTotalSeats;
        this.flightTotalPassengers = flightTotalPassengers;
    }

    // Getters and setters
    public int getFlightId() {
        return flightId;
    }
    public Date getFlightDate() {
        return flightDate;
    }
    public Time getFlightTime() {
        return flightTime;
    }
    public double getFlightPrice() {
        return flightPrice;
    }
    public int getFlightTotalSeats() {
        return flightTotalSeats;
    }
    public int getFlightTotalPassengers() {
        return flightTotalPassengers;
    }
}

// flight_date DATE NOT NULL,
//     flight_time TIME NOT NULL,
//     flight_price DECIMAL(10, 2) DEFAULT 100.00,
//     flight_total_seats INT DEFAULT 50,
//     flight_total_passengers INT DEFAULT 0,
    
