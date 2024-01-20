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
import com.flyease.server.model.WaitingListQueue;
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
        boolean add_success = orderService.addOrder(input);
        
        if (add_success) {
            return ResponseEntity.ok("Order created successfully");        
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create order");
        }
    }
    // Example input:
    // {
    //     "userId": 1,
    //     "flightId": 1,
    //     "orderTotalPrice": 100.00,
    //     "orderPaymentMethod": "FPX",
    //     "passengerFirstName": "John",
    //     "passengerLastName": "Doe",
    //     "passengerPassportNo": "123456",
    //     "passengerGender": "male",
    //     "passengerEmail": "johndoe@gmail",
    //     "passengerPhoneNo": "1234567890"
    // }

    // http://localhost:8080/order/createWaitingList
    @PostMapping("/createWaitingList")
    public ResponseEntity<String> createWaitingList(@RequestBody CreateOrderInput input) throws SQLException {
        boolean add_success = orderService.addWaitingList(input);
        
        if (add_success) {
            return ResponseEntity.ok("Waiting List created successfully");        
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create order");
        }
    }
      
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

    // http://localhost:8080/order/deleteWaitingList/{order_id}
    @DeleteMapping("/deleteWaitingList/{order_id}")
    public ResponseEntity<String> deleteWaitingList(@PathVariable int order_id) throws SQLException {
        boolean delete_success = orderService.deleteWaitingList(order_id);
        if (delete_success) {
            return ResponseEntity.ok("Waiting List deleted successfully");        
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
    // Sample output:
    // [
    //     {
    //         "order": {
    //             "orderId": 2,
    //             "userId": 1,
    //             "flightId": 1,
    //             "orderTotalPrice": 100.0,
    //             "orderPaymentMethod": "FPX",
    //             "orderTimestamp": "2024-01-20T01:07:09.000+00:00",
    //             "passengerId": 2,
    //             "status": "confirmed"
    //         },
    //         "flight": {
    //             "flightId": 1,
    //             "flightDepartureDate": "2024-01-25",
    //             "flightDepartureTime": "12:00:00",
    //             "flightArrivalDate": "2024-01-25",
    //             "flightArrivalTime": "12:00:00",
    //             "flightPrice": 100.0,
    //             "flightTotalSeats": 50,
    //             "flightTotalPassengers": 4
    //         },
    //         "passenger": {
    //             "passengerId": 2,
    //             "passengerFirstName": "John",
    //             "passengerLastName": "Doe",
    //             "passengerPassportNo": "123456",
    //             "passengerGender": "male",
    //             "passengerEmail": "johndoe@gmail",
    //             "passengerPhoneNo": "1234567890"
    //         }
    //     }
    // ]

    // http://localhost:8080/order/getAllWaitingListsByUserId/{user_id}
    @GetMapping("/getAllWaitingListsByUserId/{user_id}")
    public ResponseEntity<WaitingListQueue<OrderDetails>> getAllWaitingListsByUserId(@PathVariable int user_id) throws SQLException {
        WaitingListQueue<OrderDetails> orders = orderService.getAllWaitingListsByUserId(user_id);
        if (orders != null) {
            return ResponseEntity.ok(orders);        
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    // Sample output:
    // {
    //     "queue": [
    //         {
    //             "order": {
    //                 "orderId": 7,
    //                 "userId": 2,
    //                 "flightId": 1,
    //                 "orderTotalPrice": 100.0,
    //                 "orderPaymentMethod": "FPX",
    //                 "orderTimestamp": "2024-01-20T17:49:27.930+00:00",
    //                 "passengerId": 7,
    //                 "status": "confirmed"
    //             },
    //             "flight": {
    //                 "flightId": 1,
    //                 "flightDepartureDate": "2024-01-25",
    //                 "flightDepartureTime": "12:00:00",
    //                 "flightArrivalDate": "2024-01-25",
    //                 "flightArrivalTime": "12:00:00",
    //                 "flightPrice": 100.0,
    //                 "flightTotalSeats": 50,
    //                 "flightTotalPassengers": 4
    //             },
    //             "passenger": {
    //                 "passengerId": 7,
    //                 "passengerFirstName": "John",
    //                 "passengerLastName": "Doe",
    //                 "passengerPassportNo": "123456",
    //                 "passengerGender": "male",
    //                 "passengerEmail": "johndoe@gmail",
    //                 "passengerPhoneNo": "1234567890"
    //             }
    //         }
    //     ],
    //     "empty": false
    // }

}
