package com.ursmahesh.securex.controller;

import com.ursmahesh.securex.model.ApprovedVehicles;
import com.ursmahesh.securex.model.Vehicle;
import com.ursmahesh.securex.repo.ApprovedVehiclesRepo;
import com.ursmahesh.securex.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ApprovedVehiclesRepo approvedVehiclesRepo;

    // ✅ 1. Get all pending vehicles
    @GetMapping("/pending-vehicles")
    public List<Vehicle> getAllPendingVehicles() {
        List<Vehicle> vehicles = vehicleService.getAll();

        System.out.println("📦 Fetching all pending vehicles:");
        for (Vehicle v : vehicles) {
            System.out.println("➡️ Vehicle ID: " + v.getVehicleId());
            System.out.println("   Name: " + v.getVehicleName());
            System.out.println("   Image Type: " + v.getVehicle_image_type());
            System.out.println("   Doc Type: " + v.getVehicle_doc_type());
        //    System.out.println("   Vehicle Image Size: " + (v.getVehicleImage()));
            System.out.println("   Vehicle Doc Size: " + (v.getVehicleDoc()));
            System.out.println("------------------------------------------------");
        }

        return vehicles;
    }


    // ✅ 2. Approve a pending vehicle by ID
    @PostMapping("/approve/{vehicleId}")
    public ResponseEntity<String> approveVehicle(@PathVariable("vehicleId") String vehicleId) {
        try {
            Optional<Vehicle> opt = vehicleService.getByVehicleId(vehicleId);
            if (opt.isEmpty()) {
                return ResponseEntity.status(404).body("Pending vehicle not found");
            }

            Vehicle vehicle = opt.get();
            ApprovedVehicles approved = new ApprovedVehicles();

            // ...copy all fields
            approved.setVehicleId(vehicle.getVehicleId());
            approved.setProviderId(vehicle.getProviderId());
            approved.setVehicleName(vehicle.getVehicleName());
            approved.setModelYear(vehicle.getModelYear());
            approved.setPricePerDay(vehicle.getPricePerDay());
            approved.setPricePerHour(vehicle.getPricePerHour());
            approved.setDescription(vehicle.getDescription());
            approved.setArea(vehicle.getArea());
            approved.setBuilding(vehicle.getBuilding());
            approved.setDoorNumber(vehicle.getDoorNumber());
            approved.setCity(vehicle.getCity());
            approved.setPinCode(vehicle.getPinCode());
            approved.setLocation(vehicle.getLocation());
            approved.setVehicleImage(vehicle.getVehicleImage());
            approved.setVehicleDoc(vehicle.getVehicleDoc());
            approved.setVehicle_image_name(vehicle.getVehicle_image_name());
            approved.setVehicle_image_type(vehicle.getVehicle_image_type());
            approved.setVehicle_doc_name(vehicle.getVehicle_doc_name());
            approved.setVehicle_doc_type(vehicle.getVehicle_doc_type());

            // 🔍 Debug log
            System.out.println("Approving vehicle: " + approved.getVehicleId());

            approvedVehiclesRepo.save(approved);
            vehicleService.deleteById(vehicleId);

            return ResponseEntity.ok("Vehicle approved successfully.");

        } catch (Exception e) {
            e.printStackTrace(); // Show the actual error
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }


    // ✅ 3. Reject a pending vehicle
    @DeleteMapping("/reject/{vehicleId}")
    public ResponseEntity<String> rejectVehicle(@PathVariable("vehicleId") String vehicleId) {
        vehicleService.deleteById(vehicleId);
        return ResponseEntity.ok("Vehicle rejected and removed.");
    }
}
