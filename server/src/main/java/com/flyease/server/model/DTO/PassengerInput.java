package com.flyease.server.model.DTO;

public class PassengerInput {
    private String passengerFirstName;
    private String passengerLastName;
    private String passengerPassportNo;
    private String passengerGender;
    private String passengerEmail;
    private String passengerPhoneNo;

    public PassengerInput(String passengerFirstName, String passengerLastName, String passengerPassportNo, String passengerGender, String passengerEmail, String passengerPhoneNo){
        this.passengerFirstName = passengerFirstName;
        this.passengerLastName = passengerLastName;
        this.passengerPassportNo = passengerPassportNo; // format: yyyy-mm-dd
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
        return "passengerFirstName: " + passengerFirstName + "\npassengerLastName: " + passengerLastName + "\npassengerPassportNo: " + passengerPassportNo + "\npassengerGender: " + passengerGender + "\npassengerEmail: " + passengerEmail + "\npassengerPhoneNo: " + passengerPhoneNo;
    }
}