package com.flyease.server.controller;

import java.sql.Date;
import java.sql.Time;
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

import com.flyease.server.model.Flight;
import com.flyease.server.model.DTO.FlightInput;
import com.flyease.server.service.FlightService;

@RestController
@RequestMapping("/flight")
@CrossOrigin(origins = "*")
public class FlightController {

    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    // http://localhost:8080/flight/getAllFlightsByDate/2023-12-25
    @GetMapping("/getAllFlightsByDate/{date}")
    public ResponseEntity<List<Flight>> getAllFlightsByDate(@PathVariable String date) {
        if (flightService.getAllFlightsByDate(date) == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } else {
            return ResponseEntity.ok().body(flightService.getAllFlightsByDate(date));
        }
    }

    // http://localhost:8080/flight/getFlightById/1
    @GetMapping("/getFlightById/{flight_id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable int flight_id) {
        if (flightService.getFlightById(flight_id) == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } else {
            return ResponseEntity.ok().body(flightService.getFlightById(flight_id));
        }
    }

    // http://localhost:8080/flight/getNumAvailableSeats/1
    @GetMapping("/getNumAvailableSeats/{flight_id}")
    public ResponseEntity<Integer> getNumSeatsByFlightId(@PathVariable int flight_id) {
        if (flightService.getNumAvailableSeats(flight_id) == -1) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } else {
            return ResponseEntity.ok().body(flightService.getNumAvailableSeats(flight_id));
        }
    }

    // NOT YET IMPLEMENTED IN FRONTEND
    // http://localhost:8080/flight/getAllFlights
    @GetMapping("/getAllFlights")
    public ResponseEntity<List<Flight>> getAllFlights() {
        return ResponseEntity.ok().body(flightService.getAllFlights());
    }
    // Example output:
    // [
    //     {
    //         "flightId": 1,
    //         "flightDate": "2024-12-25",
    //         "flightTime": "12:00:00",
    //         "flightPrice": 100.0,
    //         "flightTotalSeats": 50,
    //         "flightTotalPassengers": 0
    //     }
    // ]


    // [Admin Side]
    // http://localhost:8080/flight/addFlight
    @PostMapping("/addFlight")
    public ResponseEntity<String> addFlight(@RequestBody FlightInput flightInput) {
        boolean add_success = flightService.addFlight(Date.valueOf(flightInput.getFlightDate()), Time.valueOf(flightInput.getFlightTime()), flightInput.getFlightPrice(), flightInput.getFlightTotalSeats());
        
        if (add_success) {
            return ResponseEntity.ok("Flight added successfully");        
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add flight");
        }
    }
    // Example of a request body:
    // {
    //     "flightDate": "2024-12-25",
    //     "flightTime": "12:00:00",
    //     "flightPrice": 100.00,
    //     "flightTotalSeats": 50
    // }

    // http://localhost:8080/flight/updateFlight/{flight_id}
    @PostMapping("/updateFlight/{flight_id}")
    public ResponseEntity<String> updateFlight(@PathVariable int flight_id, @RequestBody FlightInput flightInput) {
        boolean update_success = flightService.updateFlight(flight_id, Date.valueOf(flightInput.getFlightDate()), Time.valueOf(flightInput.getFlightTime()), flightInput.getFlightPrice(), flightInput.getFlightTotalSeats(), flightInput.getFlightTotalPassengers());
        if (update_success) {
            return ResponseEntity.ok("Flight updated successfully");        
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update flight");
        }
    }
    // Example of a request body:
    // {
    //     "flightDate": "2024-12-25",
    //     "flightTime": "12:00:00",
    //     "flightPrice": 150.00,
    //     "flightTotalSeats": 50
    // }

    // http://localhost:8080/flight/deleteFlight/{flight_id}
    @DeleteMapping("/deleteFlight/{flight_id}")
    public ResponseEntity<String> deleteFlight(@PathVariable int flight_id) {
        boolean delete_success = flightService.deleteFlight(flight_id);
        if (delete_success) {
            return ResponseEntity.ok("Flight deleted successfully");        
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete flight");
        }
    }
}
