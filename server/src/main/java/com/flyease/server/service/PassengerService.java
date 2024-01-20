package com.flyease.server.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Service;

import com.flyease.server.model.Passenger;

@Service
public class PassengerService {
    private Connection connection;
    
    // establishes connection to the database
    public PassengerService(Connection connection) {
        this.connection = connection;
    }

    // FUNCTIONS TO IMPLEMENT
    // [1] update passenger information
    public boolean updatePassenger(int passengerId, String firstName, String lastName, String email, String passportNo, String gender, String phoneNo) throws SQLException {
        String query = "UPDATE Passenger SET passenger_firstname = ?, passenger_lastname = ?, passenger_email = ?, passenger_passport_no = ?, passenger_gender = ?, passenger_phone_no = ? WHERE passenger_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, passportNo);
            statement.setString(5, gender);
            statement.setString(6, phoneNo);
            statement.setInt(7, passengerId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    // HELPER FUNCTIONS
    // [1] add passenger to Passenger and return the passenger id
    public int addPassenger(int userId, int flightId, String firstName, String lastName, String email, String passportNo, String gender, String phoneNo) throws SQLException {
        String query = "INSERT INTO Passenger (user_id, flight_id, passenger_firstname, passenger_lastname, passenger_passport_no, passenger_gender, passenger_email, passenger_phone_no) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            statement.setInt(1, userId);
            statement.setInt(2, flightId);
            statement.setString(3, firstName);
            statement.setString(4, lastName);
            statement.setString(5, passportNo);
            statement.setString(6, gender);
            statement.setString(7, email);
            statement.setString(8, phoneNo);
            statement.executeUpdate();

            // get the passenger id
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1; // Return -1 if passenger ID retrieval fails
    }

    // [2] delete passenger based on the passenger id from Passenger table
    public boolean deletePassengerById(int passengerId) throws SQLException {
        String query = "DELETE FROM Passenger WHERE passenger_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, passengerId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    // [3] get passenger information by passenger id
    public Passenger getPassengerById(int passengerId) throws SQLException {
        String query = "SELECT * FROM Passenger WHERE passenger_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, passengerId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String firstName = resultSet.getString("passenger_firstname");
                String lastName = resultSet.getString("passenger_lastname");
                String passportNo = resultSet.getString("passenger_passport_no");
                String gender = resultSet.getString("passenger_gender");
                String email = resultSet.getString("passenger_email");
                String phoneNo = resultSet.getString("passenger_phone_no");

                Passenger passenger = new Passenger(firstName, lastName, passportNo, gender, email, phoneNo);
                passenger.setPassengerId(passengerId);
                return passenger;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

}
