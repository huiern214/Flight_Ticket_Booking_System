package com.flyease.server.model.DTO;

import java.util.List;

public class CreateOrderInput {
    private OrderInput order;
    private List<PassengerInput> passengers;

    public CreateOrderInput(OrderInput order, List<PassengerInput> passengers) {
        this.order = order;
        this.passengers = passengers;
    }

    // Getters and setters
    public OrderInput getOrder() {
        return order;
    }
    public List<PassengerInput> getPassengers() {
        return passengers;
    }

    public void setOrder(OrderInput order) {
        this.order = order;
    }
    public void setPassengers(List<PassengerInput> passengers) {
        this.passengers = passengers;
    }
}
