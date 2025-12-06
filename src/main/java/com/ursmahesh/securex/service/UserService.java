package com.ursmahesh.securex.service;

import com.ursmahesh.securex.model.Users;
import com.ursmahesh.securex.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UsersRepo repo;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public Users register(Users user) {
        user.setPin(encoder.encode(user.getPin())); // Encrypt the password
        System.out.println(user);
        return repo.save(user);
    }

    public String verify(Users user) {
        try {
          //  System.out.println("Verifying user: " + user.getUsername());
          //  System.out.println("PIN provided: " + user.getPin());

            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPin())
            );

            if (authentication.isAuthenticated()) {
                    String token = jwtService.generateToken(user.getUsername(), "USER");
                System.out.println("Authentication successful. Token: " + token + ", Role : USER");
                return token;
            } else {
                System.err.println("Authentication failed: Invalid username or password");
                throw new RuntimeException("Authentication failed: Invalid username or password");
            }
        } catch (Exception e) {
            System.err.println("Error during authentication: " + e.getMessage());
            e.printStackTrace(); // Print the full stack trace for debugging
            throw new RuntimeException("Invalid username or password" );
        }
    }


    public boolean existsByUsername(String username) {
        return repo.findByUsername(username) != null;
    }
}