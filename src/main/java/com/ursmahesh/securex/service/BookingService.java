package com.ursmahesh.securex.service;

import com.ursmahesh.securex.model.Booking;
import com.ursmahesh.securex.repo.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookingService {
    @Autowired
    private BookingRepo bookingRepo;
    public void AddVehicle(Booking booking) {
        bookingRepo.save(booking);
    }

    public List<Map<String, Object>> getRidesWithProviderInfo(Integer userId) {
        List<Object[]> rows = bookingRepo.findAllRidesWithProviderInfoByUserId(userId);
        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] row : rows) {
            Map<String, Object> map = new HashMap<>();

            // Assuming columns from bookings table (adjust based on actual column order)
            map.put("bookingId", row[0]);
            map.put("userId", row[1]);
            map.put("vehicleId", row[2]);
            map.put("startDate", row[3]);
            map.put("endDate", row[4]);
            map.put("totalPrice", row[5]);
            map.put("providerId", row[6]);

            // Provider info appended after bookings.* in the query
            map.put("providerName", row[7]);
            map.put("mobileNumber", row[8]);
            map.put("city", row[9]);
            map.put("area", row[10]);
            map.put("providerImage", row[11]);
            map.put("vehicleImage", row[12]);


            result.add(map);
        }

        return result;
    }

    public List<Map<String, Object>> getAllBooks(Integer providerId) {
        List<Object[]> rows = bookingRepo.getBookingsByProviderId(providerId);
        List<Map<String, Object>> bookings = new ArrayList<>();

        for (Object[] row : rows) {
            Map<String, Object> map = new HashMap<>();

            map.put("bookingId", row[0]);
            map.put("userId", row[1]);
            map.put("vehicleId", row[2]);
            map.put("startDate", row[3]);
            map.put("endDate", row[4]);
            map.put("totalPrice", row[5]);
            map.put("providerId", row[6]);

            map.put("username", row[7]);
            map.put("mobileNumber", row[8]);

            String vehicleImage = (String) row[9];
            map.put("vehicleImage", vehicleImage); // no Base64 encoding needed
            map.put("vehicleImageType", row[10]);
            map.put("vehicleImageName", row[11]);

//            if (vehicleImage != null) {
//                map.put("vehicleImageType", imageType);
//                map.put("vehicleImageName", imageName);
//            }

            bookings.add(map);
        }

        return bookings;
    }

}
