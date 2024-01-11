package com.flyease.server.model;

import java.util.List;

public class OrderDetails {
    private Order order;
    private Flight flight;
    private List<Passenger> passengers;

    public OrderDetails() {
        this.order = new Order();
        this.flight = new Flight();
        this.passengers = null;
    }

    public OrderDetails(Order order, Flight flight, List<Passenger> passengers) {
        this.order = order;
        this.flight = flight;
        this.passengers = passengers;
    }

    // Getters and setters
    public Order getOrder() {
        return order;
    }
    public Flight getFlight() {
        return flight;
    }
    public List <Passenger> getPassengers() {
        return passengers;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    public void setFlight(Flight flight) {
        this.flight = flight;
    }
    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }
}
