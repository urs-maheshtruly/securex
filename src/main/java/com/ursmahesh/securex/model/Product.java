package com.ursmahesh.securex.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double price;

    // Uncomment these fields if you need them in the future
    // private String brand;
    // private String description;
    // private String category;
    // private int stockQuantity;
    // private String releaseDate;
    // private boolean productAvailable;
    // private String imageName;
    // private String imageType;
}