package com.ursmahesh.securex.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ursmahesh.securex.dto.ResponseDTO;
import com.ursmahesh.securex.model.Vehicle;
import com.ursmahesh.securex.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
public class VehicleControl {

    @Autowired
    private VehicleService service;

    @PostMapping("/addVehicle")
    public ResponseEntity<?> addVehicle(
            @RequestPart Vehicle vehicle,
            @RequestPart("vehicleimage") MultipartFile vehicleimage,
            @RequestPart("vehicledoc") MultipartFile vehicledoc,
            @RequestParam("providerId") Long providerId ) throws JsonProcessingException {

        System.out.println("Payload: " + vehicle);

        if (service.existVehicleId(vehicle.getVehicleId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(false, "Vehicle ID already exists"));
        }

        try {
            if (vehicleimage != null && !vehicleimage.isEmpty() && vehicledoc != null && !vehicledoc.isEmpty()) {
                System.out.println("Vehicle image received: " + vehicleimage.getOriginalFilename());
                System.out.println("Vehicle doc received: " + vehicledoc.getOriginalFilename());
            } else {
                System.out.println("Image or doc is missing.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(false, "Vehicle image or document is missing"));
            }

            // ✅ Pass providerId to service
            Vehicle saved = service.addVehicles(vehicle, vehicleimage, vehicledoc, providerId);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(true, "Vehicle registered successfully with ID: " + saved.getVehicleId()));
        } catch (Exception e) {
            System.err.println("Error during vehicle registration: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(false, "Failed to register vehicle"));
        }
    }
}
