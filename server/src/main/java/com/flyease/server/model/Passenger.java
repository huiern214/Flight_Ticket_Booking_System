package com.flyease.server.model;

import java.sql.Date;

public class Passenger {
    private int passengerId;
    private String passengerFirstName;
    private String passengerLastName;
    private Date passengerDob;
    private String passengerGender;
    private String passengerEmail;
    private String passengerPhoneNo;

    public Passenger(){
        this.passengerId = -1;
        this.passengerFirstName = "";
        this.passengerLastName = "";
        this.passengerDob = Date.valueOf("0000-00-00");
        this.passengerGender = "";
        this.passengerEmail = "";
        this.passengerPhoneNo = "";
    }

    public Passenger(String passengerFirstName, String passengerLastName, String passengerDob, String passengerGender, String passengerEmail, String passengerPhoneNo){
        this.passengerId = -1;
        this.passengerFirstName = passengerFirstName;
        this.passengerLastName = passengerLastName;
        this.passengerDob = Date.valueOf(passengerDob); // format: yyyy-mm-dd
        this.passengerGender = passengerGender;
        this.passengerEmail = passengerEmail;
        this.passengerPhoneNo = passengerPhoneNo;
    }

    public Passenger(String passengerFirstName, String passengerLastName, Date passengerDob, String passengerGender, String passengerEmail, String passengerPhoneNo){
        this.passengerId = -1;
        this.passengerFirstName = passengerFirstName;
        this.passengerLastName = passengerLastName;
        this.passengerDob = passengerDob;
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
    public Date getPassengerDob(){
        return passengerDob;
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
    public void setPassengerDob(Date passengerDob){
        this.passengerDob = passengerDob;
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