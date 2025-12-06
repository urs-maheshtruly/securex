package com.ursmahesh.securex.controller;

import com.ursmahesh.securex.model.Provider;
import com.ursmahesh.securex.dto.ResponseDTO; // Assuming ResponseDTO is in this package
import com.ursmahesh.securex.repo.ProviderRepo;
import com.ursmahesh.securex.service.ProviderService;
import com.ursmahesh.securex.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
public class ProviderControl {

    @Autowired
    private ProviderService service;

    @Autowired
    private ProviderRepo providerRepo;

    @PostMapping("/register-provider")
    public ResponseEntity<?> register(
            @RequestPart("provider") Provider provider,
            @RequestPart(value = "current_picture", required = false) MultipartFile picture) {

        System.out.println("Payload: " + provider);

        if (provider.getPassword().length() < 8) {
            return ResponseEntity.badRequest().body(new ResponseDTO(false, "Password should be 8 characters long"));
        }

        if (service.existsByMobileNumber(provider.getMobileNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(false, "Mobile number already exists"));
        }

        try {
            if (picture != null && !picture.isEmpty()) {
                System.out.println("Picture received: " + picture.getOriginalFilename());
                System.out.println("Content type: " + picture.getContentType());
                System.out.println("Size: " + picture.getSize() + " bytes");
            } else {
                System.out.println("No picture received or picture is empty");
                picture = null;
            }

            Provider saved = service.register(provider, picture);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(true, "Provider registered successfully with ID: " + saved.getProvider_id()));

        } catch (Exception e) {
            System.err.println("Error during provider registration: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ResponseDTO(false, "Server error: " + e.getMessage()));
        }
    }

    @PostMapping("/login-provider")
    public ResponseEntity<?> login(@RequestBody Provider provider) {
        try {
            System.out.println("Login request received for email: " + provider.getEmail());
            System.out.println("Payload received: " + provider);

            String token = service.verify(provider); // JWT generation

            Optional<Provider> authenticatedProviderOpt = providerRepo.findByEmail(provider.getEmail());

            if (authenticatedProviderOpt.isPresent()) {
                Provider authenticatedProvider = authenticatedProviderOpt.get();

                Map<String, Object> providerDetails = new HashMap<>();
                providerDetails.put("provider_id", authenticatedProvider.getProvider_id());
                providerDetails.put("name", authenticatedProvider.getName());
                providerDetails.put("email", authenticatedProvider.getEmail());
                providerDetails.put("mobile_number", authenticatedProvider.getMobileNumber());
                providerDetails.put("city", authenticatedProvider.getCity());
                providerDetails.put("area", authenticatedProvider.getArea());
                providerDetails.put("door_number", authenticatedProvider.getDoor_number());
                providerDetails.put("colony", authenticatedProvider.getColony());
                providerDetails.put("landmark", authenticatedProvider.getLandmark());
                providerDetails.put("role", "PROVIDER");

                Map<String, Object> response = new HashMap<>();
                response.put("jwtToken", token);
                response.put("user", providerDetails);
                response.put("role", "PROVIDER"); // <-- Optional redundancy

                System.out.println("provider token : " + token);
                System.out.println("Login successful. Token and provider details sent.");
                return ResponseEntity.ok(response);
            } else {
                System.err.println("Authenticated but not found in DB");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDTO(false, "Internal server error: Provider data missing."));
            }
        } catch (Exception e) {
            System.err.println("Error during provider login: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(false, "Invalid email or password"));
        }
    }



    @GetMapping("/provider-image/{email}")
    public ResponseEntity<?> getProviderImage(@PathVariable String email) {
        try {
            Optional<Provider> providerOpt = providerRepo.findByEmail(email);
            if (!providerOpt.isPresent()) {
                System.err.println("Provider not found for email: " + email);
                return ResponseEntity.notFound().build();
            }

            Provider provider = providerOpt.get();
            String base64Image = provider.getCurrent_picture_base64();

            if (base64Image == null || base64Image.isEmpty()) {
                System.err.println("No image found for provider: " + email);
                return ResponseEntity.notFound().build();
            }

            try {
                byte[] imageBytes = ImageUtil.decodeFromBase64(base64Image);
                if (imageBytes.length == 0) {
                    System.err.println("Decoded image is empty for provider: " + email);
                    return ResponseEntity.badRequest().body("Invalid image data");
                }

                String contentType = provider.getImagetype() != null ?
                        provider.getImagetype() : "image/jpeg";

                // Add caching headers
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CACHE_CONTROL, "max-age=3600")
                        .header(HttpHeaders.EXPIRES, getExpirationTimeString())
                        .body(imageBytes);
            } catch (IllegalArgumentException e) {
                System.err.println("Failed to decode base64 image for provider: " + email);
                return ResponseEntity.badRequest().body("Invalid image format");
            }
        } catch (Exception e) {
            System.err.println("Error retrieving image for provider " + email + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve image");
        }
    }

    private String getExpirationTimeString() {
        return ZonedDateTime.now(ZoneOffset.UTC)
                .plusHours(1)
                .format(DateTimeFormatter.RFC_1123_DATE_TIME);
    }

    @GetMapping("/provider-by-email/{email}")
    public ResponseEntity<?> getProviderDetailsByEmail(@PathVariable String email) {
        try {
            Optional<Provider> providerOpt = providerRepo.findByEmail(email);
            if (providerOpt.isPresent()) {
                Provider provider = providerOpt.get();
                // Create a new Provider instance or a DTO without sensitive data
                // and explicitly add the role
                Map<String, Object> sanitizedProviderDetails = new HashMap<>();
                sanitizedProviderDetails.put("provider_id", provider.getProvider_id());
                sanitizedProviderDetails.put("name", provider.getName());
                sanitizedProviderDetails.put("email", provider.getEmail());
                sanitizedProviderDetails.put("mobile_number", provider.getMobileNumber());
                sanitizedProviderDetails.put("door_number", provider.getDoor_number());
                sanitizedProviderDetails.put("colony", provider.getColony());
                sanitizedProviderDetails.put("area", provider.getArea());
                sanitizedProviderDetails.put("landmark", provider.getLandmark());
                sanitizedProviderDetails.put("city", provider.getCity());
                // Hardcode the role here for the profile details fetch as well
                sanitizedProviderDetails.put("role", "PROVIDER"); // <--- HARDCODED ROLE

                return ResponseEntity.ok(sanitizedProviderDetails); // Return the map
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error fetching provider details: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(false, "unable to  fetch"));
        }
    }
}