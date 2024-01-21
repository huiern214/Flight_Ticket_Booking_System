package com.flyease.server.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        // get all the flights after the current time
        String query = "SELECT * FROM Flight WHERE flight_departure_date >= ?";


        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, new Date(System.currentTimeMillis()));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int flight_id = resultSet.getInt("flight_id");
                Date flight_departure_date = resultSet.getDate("flight_departure_date");
                Time flight_departure_time = resultSet.getTime("flight_departure_time");
                Date flight_arrival_date = resultSet.getDate("flight_arrival_date");
                Time flight_arrival_time = resultSet.getTime("flight_arrival_time");
                double flight_price = resultSet.getDouble("flight_price");
                int flight_total_seats = resultSet.getInt("flight_total_seats");
                int flight_total_passengers = resultSet.getInt("flight_total_passengers");
                
                flights.add(new Flight(flight_id,flight_departure_date,flight_departure_time,flight_arrival_date,flight_arrival_time,flight_price,flight_total_seats,flight_total_passengers));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        Collections.sort(flights, Comparator.comparing(Flight::getFlightDepartureDate));
        return flights;
    }

    // [2] get all the flights' information based on the flight departure date
    public List<Flight> getAllFlightsByDate(String date){
        List<Flight> flights = new ArrayList<>();
        String query = "SELECT * FROM Flight WHERE flight_departure_date = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, Date.valueOf(date));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int flight_id = resultSet.getInt("flight_id");
                Date flight_departure_date = resultSet.getDate("flight_departure_date");
                Time flight_departure_time = resultSet.getTime("flight_departure_time");
                Date flight_arrival_date = resultSet.getDate("flight_arrival_date");
                Time flight_arrival_time = resultSet.getTime("flight_arrival_time");
                double flight_price = resultSet.getDouble("flight_price");
                int flight_total_seats = resultSet.getInt("flight_total_seats");
                int flight_total_passengers = resultSet.getInt("flight_total_passengers");
                flights.add(new Flight(flight_id, flight_departure_date, flight_departure_time, flight_arrival_date, flight_arrival_time, flight_price, flight_total_seats, flight_total_passengers));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        Collections.sort(flights, Comparator.comparing(Flight::getFlightDepartureTime));
        return flights;
    }

    // [3] get all the flights' information between date1 and date2
    public List<Flight> getAllFlightsBetweenDates(String date1, String date2){
        List<Flight> flights = new ArrayList<>();
        
        // SELECT * FROM Flight WHERE flight_depart_date >= ? and flight_depart_date <= ?
        String query = "SELECT * FROM Flight WHERE flight_departure_date >= ? and flight_departure_date <=?";

        // What if date1 > date2, then execute --> get empty result set
        // How to ensure date1 < date2? --> Handle this logic at Service layer
        // if date1 < current date, then date1 = current date
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            if (Date.valueOf(date1).getTime() < System.currentTimeMillis()) {
                date1 = new Date(System.currentTimeMillis()).toString();
            }

            if (Date.valueOf(date1).getTime() >= Date.valueOf(date2).getTime()) {
                statement.setDate(1, Date.valueOf(date2).getTime() < System.currentTimeMillis() ? new Date(System.currentTimeMillis()) : Date.valueOf(date2));
                statement.setDate(2, Date.valueOf(date1));
            } else {
                statement.setDate(1, Date.valueOf(date1).getTime() < System.currentTimeMillis() ? new Date(System.currentTimeMillis()) : Date.valueOf(date1));
                statement.setDate(2, Date.valueOf(date2));
            }
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int flight_id = resultSet.getInt("flight_id");
                Date flight_departure_date = resultSet.getDate("flight_departure_date");
                Time flight_departure_time = resultSet.getTime("flight_departure_time");
                Date flight_arrival_date = resultSet.getDate("flight_arrival_date");
                Time flight_arrival_time = resultSet.getTime("flight_arrival_time");
                double flight_price = resultSet.getDouble("flight_price");
                int flight_total_seats = resultSet.getInt("flight_total_seats");
                int flight_total_passengers = resultSet.getInt("flight_total_passengers");

                // execute another query: SELECT COUNT(*) AS booked_seat FROM ticket as T WHERE T.flight_id = ? AND T.status = 'CONFIRMED';
                // int booked_seats = rs.getInt(booked_seat);
                // int available_seats = flight_total_seats - booked_seats
                flights.add(new Flight(flight_id, flight_departure_date,flight_departure_time,flight_arrival_date,flight_arrival_time,flight_price,flight_total_seats,flight_total_passengers));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        Collections.sort(flights, Comparator.comparing(Flight::getFlightDepartureDate));
        return flights;
    }

    // [4] get all the flights' information based on the flight id
    public Flight getFlightById(int flight_id) {
        String query = "SELECT * FROM Flight WHERE flight_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, flight_id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Date flight_departure_date = resultSet.getDate("flight_departure_date");
                Time flight_departure_time = resultSet.getTime("flight_departure_time");
                Date flight_arrival_date = resultSet.getDate("flight_arrival_date");
                Time flight_arrive_time = resultSet.getTime("flight_arrival_time");
                double flight_price = resultSet.getDouble("flight_price");
                int flight_total_seats = resultSet.getInt("flight_total_seats");
                int flight_total_passengers = resultSet.getInt("flight_total_passengers");
                return new Flight(flight_id, flight_departure_date,flight_departure_time,flight_arrival_date,flight_arrive_time,flight_price,flight_total_seats,flight_total_passengers);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    // [5] get the number of available seats for a flight
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
    public boolean addFlight(Date flightDepartureDate, Time flightDepartureTime, Date flightArrivalDate,
            Time flightArrivalTime, double flightPrice, int flightTotalSeats) {
        String query = "INSERT INTO Flight (flight_departure_date, flight_departure_time, flight_arrival_date, flight_arrival_time, flight_price, flight_total_seats) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setDate(1, flightDepartureDate);
            statement.setTime(2, flightDepartureTime);
            statement.setDate(3, flightArrivalDate);
            statement.setTime(4, flightArrivalTime);
            statement.setDouble(5, flightPrice);
            statement.setInt(6, flightTotalSeats);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    // [2] update flight information
    public boolean updateFlight(int flight_id, Date flight_departure_date, Time flight_departure_time, Date flight_arrival_date, Time flight_arrival_time, double flight_price, int flight_total_seats) {
        String query = "UPDATE Flight SET flight_departure_date = ?, flight_departure_time = ?, flight_arrival_date = ?, flight_arrival_time = ?, flight_price = ?, flight_total_seats = ? WHERE flight_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, flight_departure_date);
            statement.setTime(2, flight_departure_time);
            statement.setDate(3, flight_arrival_date);
            statement.setTime(4, flight_arrival_time);
            statement.setDouble(5, flight_price);
            statement.setInt(6, flight_total_seats);
            statement.setInt(7, flight_id);
            
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
