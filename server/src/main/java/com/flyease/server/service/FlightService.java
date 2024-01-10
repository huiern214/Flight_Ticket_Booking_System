package com.flyease.server.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
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
                Date flight_departure_date = resultSet.getDate("flight_departure_date");
                Time flight_departure_time = resultSet.getTime("flight_departure_time");
                Date flight_arrive_Date = resultSet.getDate("flight_arrive_Date");
                Time flight_arrive_time = resultSet.getTime("flight_arrival_time");
                double flight_price = resultSet.getDouble("flight_price");
                int flight_total_seats = resultSet.getInt("flight_total_seats");
                int flight_total_passengers = resultSet.getInt("flight_total_passengers");
                double flight_business_price = resultSet.getDouble("flight_business_price");
                int flight_total_business_seats = resultSet.getInt("flight_total_business_seats");
                int flight_total_business_passengers = resultSet.getInt("flight_total_business_passengers");
                
     
                //TODO: add to this constructor
                flights.add(new Flight(flight_id,flight_departure_date,flight_departure_time,flight_arrive_Date,flight_arrive_time,flight_price,flight_total_seats,flight_total_passengers,flight_business_price,flight_total_business_seats,flight_total_business_passengers));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return flights;
    }
/* 
    // [2] get all the flights' information based on the flight date

    // What if date1 > date2, then execute --> get empty result set
    // How to ensure date1 < date2? --> Handle this logic at Service layer

    // User input: 13 Jan & 7 Jan --> FlightService.getAllFlightsBetweenDates(13 Jan 2024, 7 Jan 2024)
    // Service Layer:  --> getAllFlightsBetweenDates(date 1, date 2) 
    if (date 1 >= date 2) {
        return FlightDao.getAllFlightsByDate(date 2, date 1);
    else
        return FlightDao.getAllFlightsByDate(date 1, date 2);
    }
*/
    // public List<Flight> getAllFlightsBetweenDates(String date1, String date2)  --> DAO layer (FlightDao.java)
    public List<Flight> getAllFlightsByDate(String date1, String date2){
        List<Flight> flights = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        
        // SELECT * FROM Flight WHERE flight_depart_date >= ? and flight_depart_date <= ?
        String query = "SELECT * FROM Flight WHERE flight_departure_date >= ? and flight_arrival_date <=?";

        //DAO layer - SQL and result set
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            if (Date.valueOf(date1).getTime() >= Date.valueOf(date2).getTime()) {
                statement.setDate(1, Date.valueOf(date2));
                statement.setDate(2, Date.valueOf(date1));
            } else {
                statement.setDate(1, Date.valueOf(date1));
                statement.setDate(2, Date.valueOf(date2));
                //compare problem??
            }
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int flight_id = resultSet.getInt("flight_id");
                Date flight_departure_date = resultSet.getDate("flight_departure_date");
                Time flight_departure_time = resultSet.getTime("flight_departure_time");
                Date flight_arrive_Date = resultSet.getDate("flight_arrival_date");
                Time flight_arrive_time = resultSet.getTime("flight_arrival_time");
                double flight_price = resultSet.getDouble("flight_price");
                int flight_total_seats = resultSet.getInt("flight_total_seats");
                int flight_total_passengers = resultSet.getInt("flight_total_passengers");
                double flight_business_price = resultSet.getDouble("flight_business_price");
                int flight_total_business_seats = resultSet.getInt("flight_total_business_seats");
                int flight_total_business_passengers = resultSet.getInt("flight_total_business_passengers");

                // execute another query: SELECT COUNT(*) AS booked_seat FROM ticket as T WHERE T.flight_id = ? AND T.status = 'CONFIRMED';
                // int booked_seats = rs.getInt(booked_seat);
                // int available_seats = flight_total_seats - booked_seats
                flights.add(new Flight(flight_id, flight_departure_date,flight_departure_time,flight_arrive_Date,flight_arrive_time,flight_price,flight_total_seats,flight_total_passengers,flight_business_price,flight_total_business_seats,flight_total_business_passengers));
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
                Date flight_departure_date = resultSet.getDate("flight_departure_date");
                Time flight_departure_time = resultSet.getTime("flight_departure_time");
                Date flight_arrival_date = resultSet.getDate("flight_arrival_date");
                Time flight_arrive_time = resultSet.getTime("flight_arrival_time");
                double flight_price = resultSet.getDouble("flight_price");
                int flight_total_seats = resultSet.getInt("flight_total_seats");
                int flight_total_passengers = resultSet.getInt("flight_total_passengers");
                double flight_business_price = resultSet.getDouble("flight_business_price");
                int flight_total_business_seats = resultSet.getInt("flight_total_business_seats");
                int flight_total_business_passengers = resultSet.getInt("flight_total_business_passengers");
                return new Flight(flight_id, flight_departure_date,flight_departure_time,flight_arrival_date,flight_arrive_time,flight_price,flight_total_seats,flight_total_passengers,flight_business_price,flight_total_business_seats,flight_total_business_passengers);
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

    // [5] get number of available seats for business seats
    public int getNumAvailableBusinessSeats(int flight_id) {
        String query = "SELECT flight_total_business_seats - flight_total_business_passengers AS num_available_business_seats FROM Flight WHERE flight_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, flight_id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("num_available_business_seats");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }

    // [Admin Side]
    // [1] add flight to the database
    public boolean addFlight(Date flightDepartureDate, Time flightDepartureTime, Date flightArrivalDate,
            Time flightArrivalTime, double flightPrice, int flightTotalSeats,
            double flightBusinessPrice, int flightTotalBusinessSeats) {
        String query = "INSERT INTO Flight (flight_departure_date, flight_departure_time, flight_arrival_date, flight_arrival_time, flight_price, flight_total_seats, flight_business_price, flight_total_business_seats) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setDate(1, flightDepartureDate);
            statement.setTime(2, flightDepartureTime);
            statement.setDate(3, flightArrivalDate);
            statement.setTime(4, flightArrivalTime);
            statement.setDouble(5, flightPrice);
            statement.setInt(6, flightTotalSeats);
            // statement.setDouble(7, flightTotalPassengers);
            statement.setDouble(7, flightBusinessPrice);
            statement.setInt(8, flightTotalBusinessSeats);
            // statement.setDouble(10, flightTotalBusinessPassengers);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    // [2] update flight information
    public boolean updateFlight(int flight_id, Date flight_departure_date, Time flight_departure_time, Date flight_arrival_date, Time flight_arrival_time, double flight_price, int flight_total_seats, double flight_business_price, int flight_total_business_seats) {
        String query = "UPDATE Flight SET flight_departure_date = ?, flight_departure_time = ?, flight_arrival_date = ?, flight_arrival_time = ?, flight_price = ?, flight_total_seats = ?, flight_business_price = ?, flight_total_business_seats = ?, WHERE flight_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, flight_departure_date);
            statement.setTime(2, flight_departure_time);
            statement.setDate(3, flight_arrival_date);
            statement.setTime(4, flight_departure_time);
            statement.setDouble(5, flight_price);
            statement.setInt(6, flight_total_seats);
            statement.setDouble(7, flight_business_price);
            statement.setInt(8, flight_total_business_seats);
            statement.setInt(9, flight_id);
            
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
