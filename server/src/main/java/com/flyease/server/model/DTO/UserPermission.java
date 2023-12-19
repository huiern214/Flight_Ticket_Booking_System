package com.flyease.server.model.DTO;

public class UserPermission {
    private int userId;
    private String permission;

    public UserPermission(int userId, String permission) {
        this.userId = userId;
        this.permission = permission;
    }

    // Getters and setters
    public int getUserId(){
        return userId;
    }
    public String getPermission(){
        return permission;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }
    public void setPermission(String permission){
        this.permission = permission;
    }
}
