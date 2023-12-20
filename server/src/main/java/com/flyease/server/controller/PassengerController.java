package com.flyease.server.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flyease.server.model.Passenger;
import com.flyease.server.model.DTO.PassengerInput;
import com.flyease.server.service.PassengerService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class PassengerController {

    private final PassengerService passengerService;
    
    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    // http://localhost:8080/user/{user_id}/addPassenger
    @PostMapping("{user_id}/addPassenger")
    public ResponseEntity<String> addPassenger(@PathVariable int user_id, @RequestBody PassengerInput passengerInput) throws SQLException {
        boolean add_success = passengerService.addPassenger(user_id, passengerInput.getPassengerFirstName(), passengerInput.getPassengerLastName(), passengerInput.getPassengerEmail(), Date.valueOf(passengerInput.getPassengerDob()), passengerInput.getPassengerGender(), passengerInput.getPassengerPhoneNo());
        if (add_success) {
            return ResponseEntity.ok("Passenger added successfully");        
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add passenger");
        }
    }

    // http://localhost:8080/user/{passenger_id}/updatePassenger
    @PostMapping("{passenger_id}/updatePassenger")
    public ResponseEntity<String> updatePassenger(@PathVariable int passenger_id, @RequestBody PassengerInput passengerInput) throws SQLException {
        boolean update_success = passengerService.updatePassenger(passenger_id, passengerInput.getPassengerFirstName(), passengerInput.getPassengerLastName(), passengerInput.getPassengerEmail(), Date.valueOf(passengerInput.getPassengerDob()), passengerInput.getPassengerGender(), passengerInput.getPassengerPhoneNo());
        if (update_success) {
            return ResponseEntity.ok("Passenger updated successfully");        
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update passenger");
        }
    }

    // http://localhost:8080/user/deletePassenger
    @DeleteMapping("/deletePassenger/{passenger_id}")
    public ResponseEntity<String> deletePassenger(@PathVariable int passenger_id) throws SQLException {
        boolean delete_success = passengerService.deletePassenger(passenger_id);
        if (delete_success) {
            return ResponseEntity.ok("Passenger deleted successfully");        
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete passenger");
        }
    }    
    
    // Get all passengers for a user
    // http://localhost:8080/user/{user_id}/getAllPassengers
    @GetMapping("{user_id}/getAllPassengers")
    public ResponseEntity<List<Passenger>> getAllPassengers(@PathVariable int user_id) throws SQLException {
        List<Passenger> passengers = passengerService.getAllPassengers(user_id);
        if (passengers != null) {
            return ResponseEntity.ok(passengers);        
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
