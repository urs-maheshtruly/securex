package com.ursmahesh.securex.service;

import com.ursmahesh.securex.model.ApprovedVehicles;
import com.ursmahesh.securex.repo.ApprovedVehiclesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApprovedVehicleService {

    @Autowired
    private ApprovedVehiclesRepo approvedVehiclesRepo;

    public List<ApprovedVehicles> getAvailableVehicles(LocalDateTime start, LocalDateTime end) {
        return approvedVehiclesRepo.findAvailableVehicles(start, end);
    }
}
