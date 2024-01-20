package com.flyease.server.model.DTO;

public class FlightInput {
    private String flightDepartureDate;
    private String flightDepartureTime;
    private String flightArrivalDate;
    private String flightArrivalTime;
    private double flightPrice;
    private int flightTotalSeats;
    private int flightTotalPassengers;

    public FlightInput(String flightDepartureDate, String flightDepartureTime, String flightArrivalDate,
            String flightArrivalTime, double flightPrice, int flightTotalSeats, int flightTotalPassengers) {
        this.flightDepartureDate = flightDepartureDate;
        this.flightDepartureTime = flightDepartureTime;
        this.flightArrivalDate = flightArrivalDate;
        this.flightArrivalTime = flightArrivalTime;
        this.flightPrice = flightPrice;
        this.flightTotalSeats = flightTotalSeats;
        this.flightTotalPassengers = flightTotalPassengers;
    }



    public String getFlightDepartureDate() {
        return flightDepartureDate;
    }

    public void setFlightDepartureDate(String flightDepartureDate) {
        this.flightDepartureDate = flightDepartureDate;
    }

    public String getFlightDepartureTime() {
        return flightDepartureTime;
    }

    public void setFlightDepartureTime(String flightDepartureTime) {
        this.flightDepartureTime = flightDepartureTime;
    }

    public String getFlightArrivalDate() {
        return flightArrivalDate;
    }

    public void setFlightArrivalDate(String flightArrivalDate) {
        this.flightArrivalDate = flightArrivalDate;
    }

    public String getFlightArrivalTime() {
        return flightArrivalTime;
    }

    public void setFlightArrivalTime(String flightArrivalTime) {
        this.flightArrivalTime = flightArrivalTime;
    }

    public double getFlightPrice() {
        return flightPrice;
    }

    public void setFlightPrice(double flightPrice) {
        this.flightPrice = flightPrice;
    }

    public int getFlightTotalSeats() {
        return flightTotalSeats;
    }

    public void setFlightTotalSeats(int flightTotalSeats) {
        this.flightTotalSeats = flightTotalSeats;
    }

    public int getFlightTotalPassengers() {
        return flightTotalPassengers;
    }

    public void setFlightTotalPassengers(int flightTotalPassengers) {
        this.flightTotalPassengers = flightTotalPassengers;
    }    
}
