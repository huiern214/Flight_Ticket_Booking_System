package com.flyease.server.model.DTO;

public class PassengerInput {
    private String passengerFirstName;
    private String passengerLastName;
    private String passengerDob;
    private String passengerGender;
    private String passengerEmail;
    private String passengerPhoneNo;

    public PassengerInput(String passengerFirstName, String passengerLastName, String passengerDob, String passengerGender, String passengerEmail, String passengerPhoneNo){
        this.passengerFirstName = passengerFirstName;
        this.passengerLastName = passengerLastName;
        this.passengerDob = passengerDob; // format: yyyy-mm-dd
        this.passengerGender = passengerGender;
        this.passengerEmail = passengerEmail;
        this.passengerPhoneNo = passengerPhoneNo;
    }

    // Getters and setters
    public String getPassengerFirstName(){
        return passengerFirstName;
    }
    public String getPassengerLastName(){
        return passengerLastName;
    }
    public String getPassengerDob(){
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

    public void setPassengerFirstName(String passengerFirstName){
        this.passengerFirstName = passengerFirstName;
    }
    public void setPassengerLastName(String passengerLastName){
        this.passengerLastName = passengerLastName;
    }
    public void setPassengerDob(String passengerDob){
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