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

    
    public Flight() {
        this.flightId = -1;
        this.flightDepartureDate = Date.valueOf("0000-00-00");
        this.flightDepartureTime = Time.valueOf("00:00:00");
        this.flightArrivalDate = Date.valueOf("0000-00-0000");
        this.flightArrivalTime = Time.valueOf("00:00:00");
        this.flightPrice = 100.00;
        this.flightTotalSeats = 50;
        this.flightTotalPassengers = 0;
    }

    
    //without having flightId
    public Flight(Date flightDepartureDate, Time flightDepartureTime, Date flightArrivalDate,
            Time flightArrivalTime, double flightPrice, int flightTotalSeats, int flightTotalPassengers
            ) {
        this.flightDepartureDate = flightDepartureDate;
        this.flightDepartureTime = flightDepartureTime;
        this.flightArrivalDate = flightArrivalDate;
        this.flightArrivalTime = flightArrivalTime;
        this.flightPrice = flightPrice;
        this.flightTotalSeats = flightTotalSeats;
        this.flightTotalPassengers = flightTotalPassengers;
    }

    //with flightId
    public Flight(int flightId, Date flightDepartureDate, Time flightDepartureTime, Date flightArrivalDate,
            Time flightArrivalTime, double flightPrice, int flightTotalSeats, int flightTotalPassengers
            ) {
        this.flightId = flightId;
        this.flightDepartureDate = flightDepartureDate;
        this.flightDepartureTime = flightDepartureTime;
        this.flightArrivalDate = flightArrivalDate;
        this.flightArrivalTime = flightArrivalTime;
        this.flightPrice = flightPrice;
        this.flightTotalSeats = flightTotalSeats;
        this.flightTotalPassengers = flightTotalPassengers;
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
}