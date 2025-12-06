package com.ursmahesh.securex.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Data
public class BookingWithUserVehicleDTO {
    private Integer bookingId;
    private Integer vehicleId;
    private Date startDate;
    private Date endDate;
    private Double totalPrice;
    private String username;
    private String mobileNumber;
    private byte[] vehicleImage;
    private String vehicleImageType;
    private String vehicleImageName;

    // Constructors
    public BookingWithUserVehicleDTO(Integer bookingId, Integer vehicleId, Date startDate, Date endDate,
                                     Double totalPrice, String username, String mobileNumber,
                                     byte[] vehicleImage, String vehicleImageType, String vehicleImageName) {
        this.bookingId = bookingId;
        this.vehicleId = vehicleId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.username = username;
        this.mobileNumber = mobileNumber;
        this.vehicleImage = vehicleImage;
        this.vehicleImageType = vehicleImageType;
        this.vehicleImageName = vehicleImageName;
    }

    // Getters and setters (use Lombok @Data if you prefer)
    // ...
}
