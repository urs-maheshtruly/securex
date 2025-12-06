package com.ursmahesh.securex.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Integer bookingId;


    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "vehicle_id")
    private String vehicleId;

    @Column(name = "provider_id")
    private Integer providerId;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "total_price")
    private Integer totalPrice;


}
