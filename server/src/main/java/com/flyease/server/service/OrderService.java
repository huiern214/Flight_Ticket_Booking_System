package com.flyease.server.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.flyease.server.model.Flight;
import com.flyease.server.model.Order;
import com.flyease.server.model.OrderDetails;
import com.flyease.server.model.Passenger;
import com.flyease.server.model.DTO.CreateOrderInput;

@Service
public class OrderService {
    private Connection connection;
    private PassengerService passengerService;
    private FlightService flightService;
    
    // establishes connection to the database
    public OrderService(Connection connection, PassengerService passengerService, FlightService flightService) {
        this.connection = connection;
        this.passengerService = passengerService;
        this.flightService = flightService;
    }

    // FUNCTIONS TO IMPLEMENT
    // [1] create order to the database
    // 1. add passenger to the Passenger table
    // 2. add order to the Order table
    // 3. update flight_total_passengers in Flight table
    public boolean addOrder(CreateOrderInput createOrderInput) throws SQLException {
        // 1. add passenger to the Passenger table
        int passengerId = passengerService.addPassenger(createOrderInput.getUserId(), createOrderInput.getFlightId(), createOrderInput.getPassengerFirstName(), createOrderInput.getPassengerLastName(), createOrderInput.getPassengerEmail(), createOrderInput.getPassengerPassportNo(), createOrderInput.getPassengerGender(), createOrderInput.getPassengerPhoneNo());
        if (passengerId == -1) {
            return false;
        }
        // 2. add order to the Order table
        String query = "INSERT INTO `Order` (user_id, flight_id, order_total_price, order_payment_method, passenger_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            statement.setInt(1, createOrderInput.getUserId());
            statement.setInt(2, createOrderInput.getFlightId());
            statement.setDouble(3, createOrderInput.getOrderTotalPrice());
            statement.setString(4, createOrderInput.getOrderPaymentMethod());
            statement.setInt(5, passengerId);
            statement.executeUpdate();
            
            // 3. update flight_total_passengers in Flight table
            flightService.addNumPassengersToFlight(createOrderInput.getFlightId(), 1);
            return true;

        } catch (SQLException e) {
            System.out.println(e);
        }
        return false; // Return false if order ID retrieval fails
    }
    
    // [2] delete order based on the order id
    // 1. get passenger id from Order table using getPassengerIdFromOrder()
    // 2. get flight id from Order table using getFlightIdFromOrder()
    // 3. delete the passenger information based on the passenger id
    // 4. delete order based on the order id in Order table using deleteOrderById()
    // 5. update flight_total_passengers in Flight table using deletePassengersFromFlight()
    public boolean deleteOrder(int orderId) throws SQLException {
        // 1. get passenger id from Order table using getPassengerIdFromOrder()
        int passengerId = getPassengerIdFromOrder(orderId);
        if (passengerId == -1) {
            return false;
        }

        // 2. get flight id from Order table using getFlightIdFromOrder()
        int flightId = getFlightIdFromOrder(orderId);
        if (flightId == -1) {
            return false;
        }

        // 3. delete the passenger information based on the passenger id
        if (!passengerService.deletePassengerById(passengerId)) {
            return false;
        }

        // 4. delete order based on the order id in Order table using deleteOrderById()
        if (!deleteOrderById(orderId)) {
            return false;
        }

        // 5. update flight_total_passengers in Flight table using deletePassengersFromFlight()
        if (!flightService.deductNumPassengersFromFlight(flightId, 1)) {
            return false;
        }
        return true;
    }

    // Note: ORDER DETAILS [Order order, Flight flight, Passenger passenger]
    // [3] get order details based on the order id
    // 1. get all the orders' information from Order table
    // 2. get all the flights' information based on the flight id using getFlightById()
    // 3. get all the passengers' information based on the order id using getPassengerById()
    public OrderDetails getOrderDetails(int orderId) throws SQLException {
        // 1. get the order information from Order table
        String query = "SELECT * FROM `Order` WHERE order_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                int flightId = resultSet.getInt("flight_id");
                double orderTotalPrice = resultSet.getDouble("order_total_price");
                String orderPaymentMethod = resultSet.getString("order_payment_method");
                Timestamp orderTimestamp = resultSet.getTimestamp("order_timestamp");
                int passengerId = resultSet.getInt("passenger_id");

                Order order = new Order(orderId, userId, flightId, orderTotalPrice, orderPaymentMethod, orderTimestamp, passengerId);

                // 2. get the flight information based on the flight id using getFlightById()
                Flight flightInfo = flightService.getFlightById(flightId);

                // 3. get the passenger information based on the order id using getAllPassengersFromOrder()
                Passenger passenger = passengerService.getPassengerById(passengerId);

                return new OrderDetails(order, flightInfo, passenger);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    // [4] get all the orders' details based on the user id
    // 1. get all the orders' id from Order table
    // 2. get all the orders' information based on the order id using getOrderDetails()
    public List<OrderDetails> getAllOrdersByUserId(int userId) throws SQLException {
        List<OrderDetails> orders = new ArrayList<>();
        String query = "SELECT order_id FROM `Order` WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                OrderDetails orderDetails = getOrderDetails(orderId);
                if (orderDetails != null) {
                    orders.add(orderDetails);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return orders;
    }

    // HELPER FUNCTIONS
    // [1] get flight id from Order table
    private int getFlightIdFromOrder(int orderId) {
        String query = "SELECT flight_id FROM `Order` WHERE order_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("flight_id");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }

    // [2] get passenger id from Order table
    private int getPassengerIdFromOrder(int orderId) {
        String query = "SELECT passenger_id FROM `Order` WHERE order_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("passenger_id");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }

    // [3] delete order based on the order id in Order table
    private boolean deleteOrderById(int orderId) {
        String query = "DELETE FROM `Order` WHERE order_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }
}
