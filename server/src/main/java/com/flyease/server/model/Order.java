package com.flyease.server.model;

import java.sql.Timestamp;

public class Order {
    int orderId;
    int userId;
    int flightId;
    double orderTotalPrice;
    String orderPaymentMethod;
    Timestamp orderTimestamp;
    int ordertotalPassengers;

    public Order() {
        this.orderId = -1;
        this.userId = -1;
        this.flightId = -1;
        this.orderTotalPrice = 0.00;
        this.orderPaymentMethod = "";
        this.orderTimestamp = Timestamp.valueOf("0000-00-00 00:00:00");
        this.ordertotalPassengers = 0;
    }

    public Order(int userId, int flightId, double orderTotalPrice, String orderPaymentMethod, int ordertotalPassengers) {
        this.orderId = -1;
        this.userId = userId;
        this.flightId = flightId;
        this.orderTotalPrice = orderTotalPrice;
        this.orderPaymentMethod = orderPaymentMethod;
        this.orderTimestamp = Timestamp.valueOf("0000-00-00 00:00:00");
        this.ordertotalPassengers = ordertotalPassengers;
    }

    public Order(int orderId, int userId, int flightId, double orderTotalPrice, String orderPaymentMethod, Timestamp orderTimestamp, int ordertotalPassengers) {
        this.orderId = orderId;
        this.userId = userId;
        this.flightId = flightId;
        this.orderTotalPrice = orderTotalPrice;
        this.orderPaymentMethod = orderPaymentMethod;
        this.orderTimestamp = orderTimestamp;
        this.ordertotalPassengers = ordertotalPassengers;
    }

    // Getters and setters
    public int getOrderId() {
        return orderId;
    }
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
    public Timestamp getOrderTimestamp() {
        return orderTimestamp;
    }
    public int getOrderTotalPassengers() {
        return ordertotalPassengers;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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
    public void setOrderTimestamp(Timestamp orderTimestamp) {
        this.orderTimestamp = orderTimestamp;
    }
    public void setOrderTotalPassengers(int ordertotalPassengers) {
        this.ordertotalPassengers = ordertotalPassengers;
    }
}
