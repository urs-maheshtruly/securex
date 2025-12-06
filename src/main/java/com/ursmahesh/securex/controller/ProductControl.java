package com.ursmahesh.securex.controller;

import com.ursmahesh.securex.model.Product;
import com.ursmahesh.securex.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ProductControl {

    @Autowired
    private ProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getById(@PathVariable int id) {
        Product product = service.getProductById(id);
        if (product != null)
            return new ResponseEntity<>(product, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addproduct")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        try {
            Product product1 = service.addProduct(product);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/products/search")
//    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword) {
//        List<Product> products = service.SearchProduct(keyword);
//        return new ResponseEntity<>(products, HttpStatus.OK);
//    }
}