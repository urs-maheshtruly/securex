package com.ursmahesh.securex.controller;

import com.ursmahesh.securex.model.ApprovedVehicles;
import com.ursmahesh.securex.service.ApprovedVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
public class ApprovedVehicleControl {

    @Autowired
    private ApprovedVehicleService approvedVehicleService;

    @GetMapping("/available-vehicles")
    public ResponseEntity<List<ApprovedVehicles>> getAvailableBikes(
            @RequestParam("startDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("endDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {

        System.out.println("Start date: " + start);
        System.out.println("End date: " + end);

        List<ApprovedVehicles> available = approvedVehicleService.getAvailableVehicles(start, end);
        return ResponseEntity.ok(available);
    }

}
