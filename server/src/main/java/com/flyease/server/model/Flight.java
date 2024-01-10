package com.flyease.server.model;

import java.sql.Date;
import java.sql.Time;

public class Flight {
    private int flightId;
    private Date flightDepartureDate;
    private Time flightDepartureTime;
    private Date flightArrivalDate;
    private Time flightArrivalTime;
    private double flightPrice;
    private int flightTotalSeats;
    private int flightTotalPassengers;
    private double flightBusinessPrice;
    private int flightTotalBusinessSeats; //newly created
    private int flightTotalBusinessPassengers;

    
    public Flight() {
        this.flightId = -1;
        this.flightDepartureDate = Date.valueOf("0000-00-00");
        this.flightDepartureTime = Time.valueOf("00:00:00");
        this.flightArrivalDate = Date.valueOf("0000-00-0000");
        this.flightArrivalTime = Time.valueOf("00:00:00");
        this.flightPrice = 100.00;
        this.flightTotalSeats = 50;
        this.flightTotalPassengers = 0;
        this.flightBusinessPrice = 200.00;
        this.flightTotalBusinessSeats = 20; //newly created
        this.flightTotalBusinessPassengers = 0;
    }

    
    // //without having flightId
    // public Flight(Date flightDepartureDate, Time flightDepartureTime, Date flightArrivalDate,
    //         Time flightArrivalTime, double flightPrice, int flightTotalSeats, int flightTotalPassengers,
    //         double flightBusinessPrice, int flightTotalBusinessSeats, int flightTotalBusinessPassengers) {
    //     this.flightDepartureDate = flightDepartureDate;
    //     this.flightDepartureTime = flightDepartureTime;
    //     this.flightArrivalDate = flightArrivalDate;
    //     this.flightArrivalTime = flightArrivalTime;
    //     this.flightPrice = flightPrice;
    //     this.flightTotalSeats = flightTotalSeats;
    //     this.flightTotalPassengers = flightTotalPassengers;
    //     this.flightBusinessPrice = flightBusinessPrice;
    //     this.flightTotalBusinessSeats = flightTotalBusinessSeats;
    //     this.flightTotalBusinessPassengers = flightTotalBusinessPassengers;
    // }

    //with flightId
    public Flight(int flightId, Date flightDepartureDate, Time flightDepartureTime, Date flightArrivalDate,
            Time flightArrivalTime, double flightPrice, int flightTotalSeats, int flightTotalPassengers,
            double flightBusinessPrice, int flightTotalBusinessSeats, int flightTotalBusinessPassengers) {
        this.flightId = flightId;
        this.flightDepartureDate = flightDepartureDate;
        this.flightDepartureTime = flightDepartureTime;
        this.flightArrivalDate = flightArrivalDate;
        this.flightArrivalTime = flightArrivalTime;
        this.flightPrice = flightPrice;
        this.flightTotalSeats = flightTotalSeats;
        this.flightTotalPassengers = flightTotalPassengers;
        this.flightBusinessPrice = flightBusinessPrice;
        this.flightTotalBusinessSeats = flightTotalBusinessSeats;
        this.flightTotalBusinessPassengers = flightTotalBusinessPassengers;
    }

    // Getters and setters
    public int getFlightId() {
        return flightId;
    }

    public Date getFlightDepartureDate() {
        return flightDepartureDate;
    }

    public Time getFlightDepartureTime() {
        return flightDepartureTime;
    }

    public Date getFlightArrivalDate() {
        return flightArrivalDate;
    }

    public Time getFlightArrivalTime() {
        return flightArrivalTime;
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

    public double getFlightBusinessPrice() {
        return flightBusinessPrice;
    }

    public int getFlightTotalBusinessSeats() {
        return flightTotalBusinessSeats;
    }

    public int getFlightTotalBusinessPassengers() {
        return flightTotalBusinessPassengers;
    }

    
}

// flight_depart_date DATE NOT NULL,
//     flight_departure_time TIME NOT NULL,
//     flight_price DECIMAL(10, 2) DEFAULT 100.00,
//     flight_total_seats INT DEFAULT 50,
//     flight_total_passengers INT DEFAULT 0,
    
