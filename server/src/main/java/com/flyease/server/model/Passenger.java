package com.flyease.server.model;

import java.sql.Date;

public class Passenger {
    private int passengerId;
    private String passengerFirstName;
    private String passengerLastName;
    private String passengerPassportNo;
    private String passengerGender;
    private String passengerEmail;
    private String passengerPhoneNo;

    public Passenger(){
        this.passengerId = -1;
        this.passengerFirstName = "";
        this.passengerLastName = "";
        this.passengerPassportNo = "";
        this.passengerGender = "";
        this.passengerEmail = "";
        this.passengerPhoneNo = "";
    }

    public Passenger(String passengerFirstName, String passengerLastName, String passengerPassportNo, String passengerGender, String passengerEmail, String passengerPhoneNo){
        this.passengerId = -1;
        this.passengerFirstName = passengerFirstName;
        this.passengerLastName = passengerLastName;
        this.passengerPassportNo = passengerPassportNo;
        this.passengerGender = passengerGender;
        this.passengerEmail = passengerEmail;
        this.passengerPhoneNo = passengerPhoneNo;
    }

    // Getters and setters
    public int getPassengerId(){
        return passengerId;
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

    public void setPassengerId(int passengerId){
        this.passengerId = passengerId;
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
}