package com.ursmahesh.securex.controller;

import com.ursmahesh.securex.dto.BookingWithUserVehicleDTO;
import com.ursmahesh.securex.model.Booking;
import com.ursmahesh.securex.repo.BookingRepo;
import com.ursmahesh.securex.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
public class BookingControl {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingRepo bookingRepo;

    @PostMapping("/book-vehicle")
    public ResponseEntity<?> bookVehicle(@RequestBody Booking booking) {
        bookingService.AddVehicle(booking);
        return ResponseEntity.ok("Vehicle booked successfully");
    }

    @GetMapping("/user/{userId}/rides")
    public ResponseEntity<List<Map<String, Object>>> getAllRides(@PathVariable Integer userId) {
        List<Map<String, Object>> rides = bookingService.getRidesWithProviderInfo(userId);
        return ResponseEntity.ok(rides);
    }

    @GetMapping("/provider/{providerId}/bookings")
    public ResponseEntity<List<Map<String, Object>>> getAllBookings(@PathVariable Integer providerId) {
        List<Map<String,Object>> bookings = bookingService.getAllBooks(providerId);
        return ResponseEntity.ok(bookings);
    }

}
