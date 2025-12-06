package com.ursmahesh.securex.controller;

import com.ursmahesh.securex.dto.ResponseDTO;
import com.ursmahesh.securex.service.UserService;
import com.ursmahesh.securex.model.Users;
import com.ursmahesh.securex.repo.UsersRepo; // Import UsersRepo
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap; // Use HashMap for custom response
import java.util.Map;

@CrossOrigin
@RestController
public class UserControl {
    @Autowired
    private UserService service;

    @Autowired
    private UsersRepo usersRepo; // Inject UsersRepo to fetch user details

    @PostMapping("/register-user")
    public ResponseEntity<ResponseDTO> register(@RequestBody Users user) {
        System.out.println("details: " + user.getUsername() + " " + user.getPin() + " " + user.getMobile_number());

        if (user.getPin().length() > 4) {
            return ResponseEntity.badRequest().body(new ResponseDTO(false, "PIN must be at most 4 digits"));
        }

        // Check for existing username
        if (service.existsByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(false, "Username already exists"));
        }

        service.register(user);
        return ResponseEntity.ok(new ResponseDTO(true, "User registered successfully"));
    }

    @PostMapping("/login-user")
    public ResponseEntity<?> login(@RequestBody Users user) {
        try {
            System.out.println("Login request received for username: " + user.getUsername());
            System.out.println("Payload received: " + user);

            // Verify user credentials and get the JWT token
            String token = service.verify(user); // This token should contain the "USER" role claim

            // Fetch the full user details from the repository
            Users loggedInUser = usersRepo.findByUsername(user.getUsername());
            if (loggedInUser == null) {
                // This scenario should ideally not happen if service.verify succeeded
                System.err.println("Error: User authenticated but not found in database for username: " + user.getUsername());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDTO(false, "Internal server error: User data missing."));
            }

            // Prepare the response map containing token and user details
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);

            // Create a map for user details to send to the frontend
            Map<String, String> userDetails = new HashMap<>();
            userDetails.put("username", loggedInUser.getUsername());
            userDetails.put("mobile_number", loggedInUser.getMobile_number());
            userDetails.put("user_id", loggedInUser.getUser_id().toString());
            // Hardcode the role here as it's not stored in the database
            userDetails.put("role", "NORMAL_USER"); // <--- HARDCODED ROLE

            response.put("userDetails", userDetails); // Key for frontend to access details

            System.out.println("Login successful. Token and user details sent.");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Error during user login: " + e.getMessage());
            e.printStackTrace(); // Print the full stack trace for debugging
            // Return a consistent error response DTO
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDTO(false, "Invalid username or PIN"));
        }
    }
}