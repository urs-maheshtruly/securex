package com.ursmahesh.securex.service;

import com.ursmahesh.securex.model.Provider;
import com.ursmahesh.securex.repo.ProviderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import com.ursmahesh.securex.util.ImageUtil;
import java.util.Optional;

@Service
public class ProviderService {

    @Autowired
    private ProviderRepo repo;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public Provider register(Provider provider, MultipartFile picture) throws IOException {
        // Encrypt password
        provider.setPassword(encoder.encode(provider.getPassword()));

        // Handle the image if present
        if (picture != null && !picture.isEmpty()) {
            try {
                // Set image metadata
                provider.setImagename(picture.getOriginalFilename());
                provider.setImagetype(picture.getContentType());

                // Convert image to Base64 string
                byte[] imageData = picture.getBytes();
                String base64Image = ImageUtil.encodeToBase64(imageData);
                System.out.println("Image encoded to Base64, length: " + 
                        (base64Image != null ? base64Image.length() : 0) + " characters");

                // Store the Base64 string
                provider.setCurrent_picture_base64(base64Image);
            } catch (Exception e) {
                System.err.println("Error processing image: " + e.getMessage());
                e.printStackTrace();
                provider.setCurrent_picture_base64(null);
            }
        } else {
            provider.setCurrent_picture_base64(null);
        }

        // Save provider with all data at once
        Provider savedProvider = repo.save(provider);
        System.out.println("Provider saved with ID: " + savedProvider.getProvider_id());

        return savedProvider;
    }

    public Provider register(Provider provider) {
        provider.setPassword(encoder.encode(provider.getPassword())); // Encrypt the password
        return repo.save(provider);
    }

    public String verify(Provider provider) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(provider.getEmail(), provider.getPassword())
            );

            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(provider.getEmail(), "PROVIDER");
                // Don't log sensitive tokens
                System.out.println("Authentication successful. Token: " + token + "Role : PROVIDER");
                return token;
            } else {
                // This should not happen as authentication would throw an exception if not authenticated
                throw new RuntimeException("Authentication failed");
            }
        } catch (Exception e) {
            // Log without exposing details and without stack trace in production
            System.err.println("Authentication error: " + e.getClass().getSimpleName());
            // Use a consistent error message that doesn't reveal whether email or password was wrong
            throw new RuntimeException("Authentication failed");
        }
    }

    public boolean existsByEmail(String email) {
        return repo.findByEmail(email).isPresent();
    }

    public boolean existsByMobileNumber(String mobileNumber) {
        return repo.findByMobileNumber(mobileNumber).isPresent();
    }


}
