package com.flyease.server.model.DTO;

public class CreateOrderInput {
    private OrderInput order;
    private PassengerInput passenger;

    public CreateOrderInput(OrderInput order, PassengerInput passenger) {
        this.order = order;
        this.passenger = passenger;
    }

    // Getters and setters
    public OrderInput getOrder() {
        return order;
    }
    public PassengerInput getPassenger() {
        return passenger;
    }

    public void setOrder(OrderInput order) {
        this.order = order;
    }
    public void setPassenger(PassengerInput passenger) {
        this.passenger = passenger;
    }
}
