package com.flyease.server.model;

public class OrderDetails {
    private Order order;
    private Flight flight;
    private Passenger passenger;

    public OrderDetails() {
        this.order = new Order();
        this.flight = new Flight();
        this.passenger = null;
    }

    public OrderDetails(Order order, Flight flight, Passenger passenger) {
        this.order = order;
        this.flight = flight;
        this.passenger = passenger;
    }

    // Getters and setters
    public Order getOrder() {
        return order;
    }
    public Flight getFlight() {
        return flight;
    }
    public Passenger getPassenger() {
        return passenger;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    public void setFlight(Flight flight) {
        this.flight = flight;
    }
    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
}
