package com.ursmahesh.securex.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "providers")
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provider_id", nullable = false)
    private Integer provider_id;

    @Column(nullable = false)
    private String name;

    @Column(name = "mobile_number" , unique = true,  length = 15)
    @JsonProperty("mobile_number")
    private String mobileNumber;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    // Store images as Base64 strings to avoid BYTEA/OID conversion issues
    @Column(columnDefinition = "TEXT", nullable = true)
    private String current_picture_base64;

    @Column(nullable = false, length = 255)
    private String door_number;

    @Column(length = 100)
    private String colony;

    @Column(nullable = false, length = 255)
    private String area;

    @Column(length = 100)
    private String landmark;

    @Column(nullable = false, length = 255)
    private String city;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = true , name = "imagetype")
    private String imagetype;

    @Column(nullable = true, name = "imagename")
    private String imagename;
}
