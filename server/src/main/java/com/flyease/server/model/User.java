package com.flyease.server.model;

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String permission;

    public User(String firstName, String lastName, String email, String password) {
        this.userId = -1;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.permission = "user";
    }

    // Getters and setters
    public int getUserId(){
        return userId;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getPermission(){
        return permission;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public String getName(){
        return firstName + " " + lastName;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setPermission(String permission){
        this.permission = permission;
    }
}
