package com.flyease.server.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.flyease.server.model.User;
import com.flyease.server.model.DTO.UserPermission;

import org.mindrot.jbcrypt.BCrypt;

@Service
public class UserService {
    private Connection connection;
    
    // establishes connection to the database
    public UserService(Connection connection) {
        this.connection = connection;
    }

    // FUNCTIONS TO IMPLEMENT
    // [1] register a user to the database
    // add user to the email by their username, email and password
    public boolean addUser(String firstName, String lastName, String email, String password) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO User (user_firstname, user_lastname, user_email, user_password) VALUES (?, ?, ?, ?)")) {
            // check if email already exists
            if (checkDuplicateEmail(email)) {
                return false;
            }
            // hash the password
            String hashedPassword = hashPassword(password);
            // set the parameters to the query
            statement.setString(1, firstName);
            statement.setString(2, lastName); 
            statement.setString(3, email);
            statement.setString(4, hashedPassword);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    // [2] login a user
    // Authenticate user by email and password
    // return user_id <Integer> and user_permission <String>
    public UserPermission authenticateUser(String email, String passsword){
        String query = "SELECT user_id, user_password, user_permission FROM User WHERE user_email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    String hashedPassword = resultSet.getString("user_password");
                    boolean passwordMatch = verifyPassword(passsword, hashedPassword);
                    if (passwordMatch){
                        int userId = resultSet.getInt("user_id");
                        String userPermission = resultSet.getString("user_permission");
                        UserPermission user = new UserPermission(userId, userPermission);
                        return user;
                    }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    } 

    // [3] get user profile by user_id
    public User getUser(int userId) throws SQLException {
        String query = "SELECT user_firstname, user_lastname, user_email, user_permission FROM User WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String firstName = resultSet.getString("user_firstname");
                    String lastName = resultSet.getString("user_lastname");
                    String email = resultSet.getString("user_email");
                    String permission = resultSet.getString("user_permission");
                    User user = new User(firstName, lastName, email, permission);
                    user.setUserId(userId);
                    user.setPassword("the password is hidden");

                    return user;
                }
            }
        }
        return null;
    }

    // HELPER FUNCTIONS
    // [1] hash the password
    public static String hashPassword(String plainPassword) {
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        return hashedPassword;
    }

    // [2] verify the password
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    // [3] add validation for duplicate users
    public boolean checkDuplicateEmail(String email) throws SQLException {
        String checkDuplicateEmailQuery = "SELECT user_email FROM User WHERE user_email = ?";
        try (PreparedStatement statement = connection.prepareStatement(checkDuplicateEmailQuery)) {
            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        }
        return false;
    }

    // NOT YET IMPLEMENTED
    // [1] delete a user from the database
    public boolean deleteUser(int userId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM User WHERE user_id = ?")) {
            statement.setInt(1, userId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    // [2] update a user's email
    public void updateUserEmail(int userId, String newEmail) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE User SET user_email = ? WHERE user_id = ?")) {
            statement.setString(1, newEmail);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to update user email");
        }
    }

    // [3] update a user's password
    public void updateUserPassword(int userId, String oldPassword, String newPassword) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE User SET user_password = ? WHERE user_id = ?")) {
            
            // Check if old password is correct
            String checkOldPasswordQuery = "SELECT user_password FROM User WHERE user_id = ?";
            try (PreparedStatement checkOldPasswordStatement = connection.prepareStatement(checkOldPasswordQuery)) {
                checkOldPasswordStatement.setInt(1, userId);

                try (ResultSet resultSet = checkOldPasswordStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String hashedPassword = resultSet.getString("user_password");
                        boolean passwordMatch = verifyPassword(oldPassword, hashedPassword);
                        if (!passwordMatch) {
                            throw new SQLException("Old password is incorrect");
                        }
                    }
                }
            }
            // hash the new password
            // update the password
            String hashedPassword = hashPassword(newPassword);
            statement.setString(1, hashedPassword);
            statement.setInt(2, userId);
            statement.executeUpdate();
        }
    }
}