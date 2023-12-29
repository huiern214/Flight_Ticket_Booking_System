package com.flyease.server.service;

import java.sql.Timestamp;

public class WaitingListEntry {
    private int waitingListId;
    private int flightId;
    private int passengerId;
    private String status;
    private Timestamp dateAdded;

    // Constructors, getters, and setters

    public WaitingListEntry() {
        // Default constructor
    }

    public WaitingListEntry(int waitingListId, int flightId, int passengerId, String status, Timestamp dateAdded) {
        this.waitingListId = waitingListId;
        this.flightId = flightId;
        this.passengerId = passengerId;
        this.status = status;
        this.dateAdded = dateAdded;
    }

    // Getters and Setters

    public int getWaitingListId() {
        return waitingListId;
    }

    public void setWaitingListId(int waitingListId) {
        this.waitingListId = waitingListId;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public String toString() {
        return "WaitingListEntry{" +
                "waitingListId=" + waitingListId +
                ", flightId=" + flightId +
                ", passengerId=" + passengerId +
                ", status='" + status + '\'' +
                ", dateAdded=" + dateAdded +
                '}';
    }
}
