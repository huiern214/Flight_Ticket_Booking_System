package com.flyease.server.model.DTO;

public class FlightInput {
    private String flightDate;
    private String flightTime;
    private double flightPrice;
    private int flightTotalSeats;
    private int flightTotalPassengers;

    public FlightInput(String flightDate, String flightTime, double flightPrice, int flightTotalSeats) {
        this.flightDate = flightDate;
        this.flightTime = flightTime;
        this.flightPrice = flightPrice;
        this.flightTotalSeats = flightTotalSeats;
        this.flightTotalPassengers = 0;
    }

    // Getters and setters
    public String getFlightDate() {
        return flightDate;
    }
    public String getFlightTime() {
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

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }
    public void setFlightTime(String flightTime) {
        this.flightTime = flightTime;
    }
    public void setFlightPrice(double flightPrice) {
        this.flightPrice = flightPrice;
    }
    public void setFlightTotalSeats(int flightTotalSeats) {
        this.flightTotalSeats = flightTotalSeats;
    }
}
