package com.flyease.server.service;

import java.sql.Connection;
import java.sql.Date;
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
import com.flyease.server.model.DTO.OrderInput;
import com.flyease.server.model.DTO.PassengerInput;

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
    // 1. add order to the Order table
    // 2. add List<Passenger> passengers to the Passenger table using passengerService.addPassenger()
    // 3. add order_passenger to the database using the order id and passenger id using addOrderPassenger()
    // 4. update flight_total_passengers in Flight table
    public boolean addOrder(OrderInput order, List<PassengerInput> passengers) throws SQLException {
        // 1. add order to the Order table
        String query = "INSERT INTO `Order` (user_id, flight_id, order_total_price, order_payment_method, order_total_passenger) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            statement.setInt(1, order.getUserId());
            statement.setInt(2, order.getFlightId());
            statement.setDouble(3, order.getOrderTotalPrice());
            statement.setString(4, order.getOrderPaymentMethod());
            statement.setInt(5, order.getOrderTotalPassengers());
            statement.executeUpdate();
            
            // get the order id
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1);
                // 2. add List<Passenger> passengers to the Passenger table
                for (PassengerInput passengerInput : passengers) {
                    int passengerId = passengerService.addPassenger(order.getUserId(), order.getFlightId(), passengerInput.getPassengerFirstName(), passengerInput.getPassengerLastName(), passengerInput.getPassengerEmail(), Date.valueOf(passengerInput.getPassengerDob()), passengerInput.getPassengerGender(), passengerInput.getPassengerPhoneNo());
                    // 3. add list of order_passenger to the database using the order id and passenger id
                    if (!addOrderPassenger(orderId, passengerId)) {
                        return false;
                    }
                }
                // 4. update flight_total_passengers in Flight table
                flightService.addNumPassengersToFlight(order.getFlightId(), passengers.size());
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false; // Return false if order ID retrieval fails
    }
    
    // [2] delete order based on the order id
    // 1. get total passengers in the order from Order table using getTotalPassengersInOrder()
    // 2. get flight id from Order table using getFlightIdFromOrder()
    // 3. delete all the passengers' information based on the order id using
    //    in Passenger table and Order_Passenger table using deleteAllPassengersFromOrder()
    // 4. delete order based on the order id in Order table using deleteOrderById()
    // 5. update flight_total_passengers in Flight table using deletePassengersFromFlight()
    public boolean deleteOrder(int orderId) throws SQLException {
        // 1. get total passengers in the order from Order table
        int numberOfPassengers = getTotalPassengersInOrder(orderId);
        if (numberOfPassengers == -1) {
            return false;
        }

        // 2. get flight id from Order table using getFlightIdFromOrder()
        int flightId = getFlightIdFromOrder(orderId);
        if (flightId == -1) {
            return false;
        }

        // 3. delete all the passengers' information based on the order id 
        //    in Passenger table and Order_Passenger table using deleteAllPassengersFromOrder()
        if (!passengerService.deleteAllPassengersFromOrder(orderId)) {
            return false;
        }

        // 4. delete order based on the order id in Order table using deleteOrderById()
        if (!deleteOrderById(orderId)) {
            return false;
        }

        // 5. update flight_total_passengers in Flight table using deletePassengersFromFlight()
        if (!flightService.deductNumPassengersFromFlight(flightId, numberOfPassengers)) {
            return false;
        }
        return true;
    }

    // Note: ORDER DETAILS [Order order, Flight flight, List<Passenger> passengers]
    // [3] get order details based on the order id
    // 1. get all the orders' information from Order table
    // 2. get all the flights' information based on the flight id using getFlightById()
    // 3. get all the passengers' information based on the order id using getAllPassengersFromOrder()
    public OrderDetails getOrderDetails(int orderId) throws SQLException {
        // 1. get all the orders' information from Order table
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
                int orderTotalPassengers = resultSet.getInt("order_total_passenger");

                Order order = new Order(orderId, userId, flightId, orderTotalPrice, orderPaymentMethod, orderTimestamp, orderTotalPassengers);

                // 2. get all the flights' information based on the flight id using getFlightById()
                Flight flightInfo = flightService.getFlightById(flightId);

                // 3. get all the passengers' information based on the order id using getAllPassengersFromOrder()
                List<Passenger> passengers = passengerService.getAllPassengersFromOrder(orderId);

                return new OrderDetails(order, flightInfo, passengers);
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
    // [1] add order_passenger to the database
    private boolean addOrderPassenger(int orderId, int passengerId) {
        String query = "INSERT INTO Order_Passenger (order_id, passenger_id) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, orderId);
            statement.setInt(2, passengerId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    // [2] get total passengers in the order from Order table
    private int getTotalPassengersInOrder(int orderId) {
        String query = "SELECT order_total_passenger FROM `Order` WHERE order_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("order_total_passenger");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }

    // [3] get flight id from Order table
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

    // [4] delete order based on the order id in Order table
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
