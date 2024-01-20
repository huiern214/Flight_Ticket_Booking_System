package com.flyease.server.controller;

import java.sql.SQLException;
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

import com.flyease.server.model.OrderDetails;
import com.flyease.server.model.DTO.CreateOrderInput;
import com.flyease.server.service.OrderService;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;
    
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // http://localhost:8080/order/createOrder
    @PostMapping("/createOrder")
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderInput input) throws SQLException {
        boolean add_success = orderService.addOrder(input.getOrder(), input.getPassenger());
        
        if (add_success) {
            return ResponseEntity.ok("Order created successfully");        
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create order");
        }
    }
    // Example input:
    // {
    //     "order": {
    //       "userId": 1,
    //       "flightId": 1,
    //       "orderTotalPrice": 100.00,
    //       "orderPaymentMethod": "FPX",
    //       "orderTotalPassengers": 1
    //     },
    //     "passenger":
    //       {
    //         "passengerFirstName": "John",
    //         "passengerLastName": "Doe",
    //         "passengerDob": "2000-01-01",
    //         "passengerGender": "male",
    //         "passengerEmail": "johndoe@gmail",
    //         "passengerPhoneNo": "1234567890"
    //       }
    // }
      
    // http://localhost:8080/order/deleteOrder/{order_id}
    @DeleteMapping("/deleteOrder/{order_id}")
    public ResponseEntity<String> deleteOrder(@PathVariable int order_id) throws SQLException {
        boolean delete_success = orderService.deleteOrder(order_id);
        if (delete_success) {
            return ResponseEntity.ok("Order deleted successfully");        
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete order");
        }
    }

    // http://localhost:8080/order/getOrderDetails/{order_id}
    @GetMapping("/getOrderDetails/{order_id}")
    public ResponseEntity<OrderDetails> getOrderDetails(@PathVariable int order_id) throws SQLException {
        OrderDetails orderDetails = orderService.getOrderDetails(order_id);
        if (orderDetails != null) {
            return ResponseEntity.ok(orderDetails);        
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // http://localhost:8080/order/getAllOrdersByUserId/{user_id}
    @GetMapping("/getAllOrdersByUserId/{user_id}")
    public ResponseEntity<List<OrderDetails>> getAllOrdersByUserId(@PathVariable int user_id) throws SQLException {
        List<OrderDetails> orders = orderService.getAllOrdersByUserId(user_id);
        if (orders != null) {
            return ResponseEntity.ok(orders);        
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
