package com.ursmahesh.securex.repo;

import com.ursmahesh.securex.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking,Integer> {
    Booking findByBookingId(Integer bookingId);

    @Query(value = "SELECT b.*, p.name, p.mobile_number, p.city, p.area, p.current_picture_base64, v.vehicle_image, v.vehicle_image_type, v.vehicle_image_name " +
            "FROM bookings b " +
            "JOIN providers p ON b.provider_id = p.provider_id " +
            "JOIN vehicles v ON b.vehicle_id = v.vehicle_id " +
            "WHERE b.user_id = :userId", nativeQuery = true)
    List<Object[]> findAllRidesWithProviderInfoByUserId(@Param("userId") Integer userId);

    @Query(value = "SELECT b.booking_id, b.user_id, b.vehicle_id, b.start_date, b.end_date, b.total_price, " +
            "b.provider_id, u.username, u.mobile_number, v.vehicle_image, v.vehicle_image_type, v.vehicle_image_name " +
            "FROM bookings b " +
            "JOIN users u ON b.user_id = u.user_id " +
            "JOIN vehicles v ON b.vehicle_id = v.vehicle_id " +
            "WHERE b.provider_id = :providerId", nativeQuery = true)
    List<Object[]> getBookingsByProviderId(@Param("providerId") Integer providerId);


}
