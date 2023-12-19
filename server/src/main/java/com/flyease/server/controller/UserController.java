package com.flyease.server.controller;

import java.sql.SQLException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.flyease.server.model.User;
import com.flyease.server.model.DTO.UserPermission;
import com.flyease.server.model.DTO.UserRegister;
import com.flyease.server.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // http://localhost:8080/user/register
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegister user) {
        boolean success = false;
        try {
            // Add validation for duplicate users
            if (userService.checkDuplicateEmail(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
            }
            success = userService.addUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (success) {
            return ResponseEntity.ok("User registered successfully");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register user");
    }
    // Example input:
    // {
    //     "firstName": "Ali",
    //     "lastName": "Abdul",
    //     "email": "ali@gmail.com",
    //     "password": "12345"
    //   }      

    // http://localhost:8080/user/login
    @PostMapping("/login")
    public ResponseEntity<UserPermission> loginUser(@RequestBody Map<String, String> loginInfo) {
        String email = loginInfo.get("email");
        String password = loginInfo.get("password");

        UserPermission userPermission = userService.authenticateUser(email, password);
        if (userPermission != null) {
            return ResponseEntity.ok(userPermission);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
    // Example input:
    // {
    //     "email": "ali@gmail.com",
    //     "password": "12345"
    // }

    // http://localhost:8080/user/{user_id}
    @GetMapping("/{user_id}")
    public ResponseEntity<User> getUserProfile(@PathVariable int user_id) {
        try {
            User userProfile = userService.getUser(user_id);
            if (userProfile != null) {
                return ResponseEntity.ok(userProfile);
            }
            return ResponseEntity.notFound().build();
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    // Example output:
    // {
    //     "userId": 1,
    //     "firstName": "Ali",
    //     "lastName": "Abdul",
    //     "email": "Ali@gmail.com",
    //     "password": "$2a$10$y/yqIxAr/TbJa.8Mb66AkuBZINa81feMM7tpa1FQOSLwcWfxlJ78S",
    // }
}