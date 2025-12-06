package com.ursmahesh.securex.service;

import com.ursmahesh.securex.model.Vehicle;
import com.ursmahesh.securex.repo.VehicleRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.ursmahesh.securex.util.ImageUtil;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepo repo;

    public Vehicle addVehicles(Vehicle vehicle,
                               MultipartFile vehicleimage,
                               MultipartFile vehicledoc,
                               Long providerId) throws IOException {

        if (vehicleimage != null && !vehicleimage.isEmpty() &&
                vehicledoc != null && !vehicledoc.isEmpty()) {
            try {
                // ✅ Handle image
                vehicle.setVehicle_image_name(vehicleimage.getOriginalFilename());
                vehicle.setVehicle_image_type(vehicleimage.getContentType());
                byte[] vehicleImageData = vehicleimage.getBytes();
                String base64VehicleImage = ImageUtil.encodeToBase64(vehicleImageData);

                // ✅ Safely print first 50 chars of base64
                System.out.println("Base64 Vehicle Image (first 50 chars): " +
                        base64VehicleImage.substring(0, Math.min(50, base64VehicleImage.length())));

                vehicle.setVehicleImage(base64VehicleImage);

                // ✅ Handle doc
                vehicle.setVehicle_doc_name(vehicledoc.getOriginalFilename());
                vehicle.setVehicle_doc_type(vehicledoc.getContentType());
                byte[] vehicleDocData = vehicledoc.getBytes();
                String base64VehicleDoc = ImageUtil.encodeToBase64(vehicleDocData);

                System.out.println("Base64 Vehicle Doc (first 50 chars): " +
                        base64VehicleDoc.substring(0, Math.min(50, base64VehicleDoc.length())));

                vehicle.setVehicleDoc(base64VehicleDoc);

                // ✅ Set provider ID
                vehicle.setProviderId(providerId);

            } catch (Exception e) {
                System.out.println("Error during vehicle registration: " + e.getMessage());
                return null;
            }

        } else {
            System.out.println("No vehicle image or doc received or image or doc is empty");
            return null;
        }

        // ✅ Save vehicle
        Vehicle savedVehicle = repo.save(vehicle);
        System.out.println("Vehicle saved with ID: " + savedVehicle.getVehicleId());
        return savedVehicle;
    }

    public boolean existVehicleId(String vehicleId) {
        return repo.findByVehicleId(vehicleId).isPresent();
    }

    public List<Vehicle> getAll() {
        return repo.findAll();
    }

    public Optional<Vehicle> getByVehicleId(String vehicleId) {
        return repo.findByVehicleId(vehicleId);
    }

    @Transactional
    public void deleteById(String vehicleId) {
        repo.deleteByVehicleId(vehicleId);
    }
}
