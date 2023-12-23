package com.flyease.server.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.flyease.server.model.Flight;

@Service
public class FlightService {
    private Connection connection;
    
    // establishes connection to the database
    public FlightService(Connection connection) {
        this.connection = connection;
    }

    // FUNCTIONS TO IMPLEMENT
    // [1] get all the flights' information
    public List<Flight> getAllFlights() {
        List<Flight> flights = new ArrayList<>();
        String query = "SELECT * FROM Flight";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int flight_id = resultSet.getInt("flight_id");
                Date flight_date = resultSet.getDate("flight_date");
                Time flight_time = resultSet.getTime("flight_time");
                double flight_price = resultSet.getDouble("flight_price");
                int flight_total_seats = resultSet.getInt("flight_total_seats");
                int flight_total_passengers = resultSet.getInt("flight_total_passengers");
                flights.add(new Flight(flight_id, flight_date, flight_time, flight_price, flight_total_seats, flight_total_passengers));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return flights;
    }

    // [2] get all the flights' information based on the flight date
    public List<Flight> getAllFlightsByDate(String date){
        List<Flight> flights = new ArrayList<>();
        String query = "SELECT * FROM Flight WHERE flight_date = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, Date.valueOf(date));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int flight_id = resultSet.getInt("flight_id");
                Date flight_date = resultSet.getDate("flight_date");
                Time flight_time = resultSet.getTime("flight_time");
                double flight_price = resultSet.getDouble("flight_price");
                int flight_total_seats = resultSet.getInt("flight_total_seats");
                int flight_total_passengers = resultSet.getInt("flight_total_passengers");
                flights.add(new Flight(flight_id, flight_date, flight_time, flight_price, flight_total_seats, flight_total_passengers));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return flights;
    }

    // [3] get all the flights' information based on the flight id
    public Flight getFlightById(int flight_id) {
        String query = "SELECT * FROM Flight WHERE flight_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, flight_id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Date flight_date = resultSet.getDate("flight_date");
                Time flight_time = resultSet.getTime("flight_time");
                double flight_price = resultSet.getDouble("flight_price");
                int flight_total_seats = resultSet.getInt("flight_total_seats");
                int flight_total_passengers = resultSet.getInt("flight_total_passengers");
                return new Flight(flight_id, flight_date, flight_time, flight_price, flight_total_seats, flight_total_passengers);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    // [4] get the number of available seats for a flight
    public int getNumAvailableSeats(int flight_id) {
        String query = "SELECT flight_total_seats - flight_total_passengers AS num_available_seats FROM Flight WHERE flight_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, flight_id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("num_available_seats");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }

     // [Admin Side]
    // [1] add flight to the database
    public boolean addFlight(Date flight_date, Time flight_time, double flight_price, int flight_total_seats) {
        String query = "INSERT INTO Flight (flight_date, flight_time, flight_price, flight_total_seats) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setDate(1, flight_date);
            statement.setTime(2, flight_time);
            statement.setDouble(3, flight_price);
            statement.setInt(4, flight_total_seats);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    // [2] update flight information
    public boolean updateFlight(int flight_id, Date flight_date, Time flight_time, double flight_price, int flight_total_seats, int flight_total_passengers) {
        String query = "UPDATE Flight SET flight_date = ?, flight_time = ?, flight_price = ?, flight_total_seats = ? WHERE flight_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, flight_date);
            statement.setTime(2, flight_time);
            statement.setDouble(3, flight_price);
            statement.setInt(4, flight_total_seats);
            statement.setInt(5, flight_id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    // [3] delete flight from the database
    public boolean deleteFlight(int flight_id) {
        String query = "DELETE FROM Flight WHERE flight_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, flight_id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }


    // HELPER FUNCTIONS
    // [1] add numPassengers to a flight
    public boolean addNumPassengersToFlight(int flight_id, int num_passengers) {
        String query = "UPDATE Flight SET flight_total_passengers = flight_total_passengers + ? WHERE flight_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, num_passengers);
            statement.setInt(2, flight_id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    // [2] deduct numPassengers from a flight
    public boolean deductNumPassengersFromFlight(int flight_id, int num_passengers) {
        String query = "UPDATE Flight SET flight_total_passengers = flight_total_passengers - ? WHERE flight_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, num_passengers);
            statement.setInt(2, flight_id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }
    
}
