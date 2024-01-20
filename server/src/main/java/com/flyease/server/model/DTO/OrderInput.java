package com.flyease.server.model.DTO;

public class OrderInput {
    private int userId;
    private int flightId;
    private double orderTotalPrice;
    private String orderPaymentMethod;

    public OrderInput(int userId, int flightId, double orderTotalPrice, String orderPaymentMethod) {
        this.userId = userId;
        this.flightId = flightId;
        this.orderTotalPrice = orderTotalPrice;
        this.orderPaymentMethod = orderPaymentMethod;
    }

    // Getters and setters
    public int getUserId() {
        return userId;
    }
    public int getFlightId() {
        return flightId;
    }
    public double getOrderTotalPrice() {
        return orderTotalPrice;
    }
    public String getOrderPaymentMethod() {
        return orderPaymentMethod;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }
    public void setOrderTotalPrice(double orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }
    public void setOrderPaymentMethod(String orderPaymentMethod) {
        this.orderPaymentMethod = orderPaymentMethod;
    }
}
