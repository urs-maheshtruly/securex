package com.ursmahesh.securex.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApprovedVehicles {

    @Id
    @Column(name = "vehicle_id")
    private String vehicleId;

    @Column(name = "provider_id")
    private long providerId;

    @Column(name = "price_per_hour")
    private Double pricePerHour;

    @Column(name = "price_per_day")
    private Double pricePerDay;


    @Column(name = "vehicle_image" , columnDefinition = "TEXT" , nullable = false)
    private String vehicleImage;


    @Column(name = "vehicle_document", columnDefinition = "TEXT", nullable = false)
    private String vehicleDoc;

    @Column(columnDefinition = "TEXT" , name = "description")
    private String description;

    @Column(name = "area")
    private String area;

    @Column(name = "building")
    private String building;

    @Column(name = "door_number")
    private String doorNumber;

    @Column(name = "city")
    private String city;

    @Column(name = "vehicle_name")
    private String vehicleName;

    @Column(name = "model_year")
    private String modelYear;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(name = "location")
    private String location;

    @Column(name = "vehicle_image_name" , nullable = false)
    private String vehicle_image_name;

    @Column(name ="vehicle_image_type" , nullable = false)
    private String vehicle_image_type;

    @Column(name = "vehicle_doc_type" , nullable = false)
    private String vehicle_doc_type;

    @Column(name = "vehicle_doc_name" , nullable = false)
    private String vehicle_doc_name;
}
