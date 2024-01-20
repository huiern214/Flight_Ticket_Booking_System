package com.flyease.server.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flyease.server.model.DTO.PassengerInput;
import com.flyease.server.service.PassengerService;

@RestController
@RequestMapping("/passenger")
@CrossOrigin(origins = "*")
public class PassengerController {

    private final PassengerService passengerService;
    
    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    // http://localhost:8080/passenger/updatePassenger/{passenger_id}
    @PostMapping("/updatePassenger/{passenger_id}")
    public ResponseEntity<String> updatePassenger(@PathVariable int passenger_id, @RequestBody PassengerInput passengerInput) throws SQLException {
        boolean update_success = passengerService.updatePassenger(passenger_id, passengerInput.getPassengerFirstName(), passengerInput.getPassengerLastName(), passengerInput.getPassengerEmail(), passengerInput.getPassengerPassportNo(), passengerInput.getPassengerGender(), passengerInput.getPassengerPhoneNo());
        if (update_success) {
            return ResponseEntity.ok("Passenger updated successfully");        
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update passenger");
        }
    }
    // Example input:
    // {
    //     "passengerFirstName": "John",
    //     "passengerLastName": "Doe",
    //     "passengerPassportNo": "1234567",
    //     "passengerGender": "male",
    //     "passengerEmail": "johndoe@gmail.com",
    //     "passengerPhoneNo": "1234567890"
    // }
}
