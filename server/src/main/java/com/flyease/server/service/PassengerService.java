package com.flyease.server.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.flyease.server.model.Passenger;

@Service
public class PassengerService {
    private Connection connection;
    
    // establishes connection to the database
    public PassengerService(Connection connection) {
        this.connection = connection;
    }

    // add passenger to the database based on the user id
    public boolean addPassenger(int user_id, String first_name, String last_name, String email, Date dob, String gender, String phone_no) throws SQLException {
        String query = "INSERT INTO Passenger (user_id, passenger_firstname, passenger_lastname, passenger_dob, passenger_gender, passenger_email, passenger_phone_no) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, user_id);
            statement.setString(2, first_name);
            statement.setString(3, last_name);
            statement.setDate(4, dob);
            statement.setString(5, gender);
            statement.setString(6, email);
            statement.setString(7, phone_no);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    // update passenger information
    public boolean updatePassenger(int passenger_id, String first_name, String last_name, String email, Date dob, String gender, String phone_no) throws SQLException {
        String query = "UPDATE Passenger SET passenger_firstname = ?, passenger_lastname = ?, passenger_email = ?, passenger_dob = ?, passenger_gender = ?, passenger_phone_no = ? WHERE passenger_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, first_name);
            statement.setString(2, last_name);
            statement.setString(3, email);
            statement.setDate(4, dob);
            statement.setString(5, gender);
            statement.setString(6, phone_no);
            statement.setInt(7, passenger_id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    // get all the passengers' information based on the user id
    public List<Passenger> getAllPassengers(int user_id) throws SQLException {
        List<Passenger> passengers = new ArrayList<>();
        String query = "SELECT * FROM Passenger WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user_id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int passenger_id = resultSet.getInt("passenger_id");
                String first_name = resultSet.getString("passenger_firstname");
                String last_name = resultSet.getString("passenger_lastname");
                Date dob = resultSet.getDate("passenger_dob");
                String gender = resultSet.getString("passenger_gender");
                String email = resultSet.getString("passenger_email");
                String phone_no = resultSet.getString("passenger_phone_no");

                Passenger passenger = new Passenger(first_name, last_name, dob, gender, email, phone_no);
                passenger.setPassengerId(passenger_id);
                passengers.add(passenger);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return passengers;
    }

    // delete passenger based on the passenger id
    public boolean deletePassenger(int passenger_id) throws SQLException {
        String query = "DELETE FROM Passenger WHERE passenger_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, passenger_id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }
}
