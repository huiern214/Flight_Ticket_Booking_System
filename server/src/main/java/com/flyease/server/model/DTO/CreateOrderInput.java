package com.flyease.server.model.DTO;

public class CreateOrderInput {
    private int userId;
    private int flightId;
    private double orderTotalPrice;
    private String orderPaymentMethod;
    private String passengerFirstName;
    private String passengerLastName;
    private String passengerPassportNo;
    private String passengerGender;
    private String passengerEmail;
    private String passengerPhoneNo;

    public CreateOrderInput(int userId, int flightId, double orderTotalPrice, String orderPaymentMethod, String passengerFirstName, String passengerLastName, String passengerPassportNo, String passengerGender, String passengerEmail, String passengerPhoneNo) {
        this.userId = userId;
        this.flightId = flightId;
        this.orderTotalPrice = orderTotalPrice;
        this.orderPaymentMethod = orderPaymentMethod;
        this.passengerFirstName = passengerFirstName;
        this.passengerLastName = passengerLastName;
        this.passengerPassportNo = passengerPassportNo;
        this.passengerGender = passengerGender;
        this.passengerEmail = passengerEmail;
        this.passengerPhoneNo = passengerPhoneNo;
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
    public String getPassengerFirstName(){
        return passengerFirstName;
    }
    public String getPassengerLastName(){
        return passengerLastName;
    }
    public String getPassengerPassportNo(){
        return passengerPassportNo;
    }
    public String getPassengerGender(){
        return passengerGender;
    }
    public String getPassengerEmail(){
        return passengerEmail;
    }
    public String getPassengerPhoneNo(){
        return passengerPhoneNo;
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
    public void setPassengerFirstName(String passengerFirstName){
        this.passengerFirstName = passengerFirstName;
    }
    public void setPassengerLastName(String passengerLastName){
        this.passengerLastName = passengerLastName;
    }
    public void setPassengerPassportNo(String passengerPassportNo){
        this.passengerPassportNo = passengerPassportNo;
    }
    public void setPassengerGender(String passengerGender){
        this.passengerGender = passengerGender;
    }
    public void setPassengerEmail(String passengerEmail){
        this.passengerEmail = passengerEmail;
    }
    public void setPassengerPhoneNo(String passengerPhoneNo){
        this.passengerPhoneNo = passengerPhoneNo;
    }

    @Override
    public String toString() {
        return "CreateOrderInput{" +
                "userId=" + userId +
                ", flightId=" + flightId +
                ", orderTotalPrice=" + orderTotalPrice +
                ", orderPaymentMethod='" + orderPaymentMethod + '\'' +
                ", passengerFirstName='" + passengerFirstName + '\'' +
                ", passengerLastName='" + passengerLastName + '\'' +
                ", passengerPassportNo='" + passengerPassportNo + '\'' +
                ", passengerGender='" + passengerGender + '\'' +
                ", passengerEmail='" + passengerEmail + '\'' +
                ", passengerPhoneNo='" + passengerPhoneNo + '\'' +
                '}';
    }
}
