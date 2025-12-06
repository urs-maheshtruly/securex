package com.ursmahesh.securex.service;

import com.ursmahesh.securex.model.Product;
import com.ursmahesh.securex.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Product getProductById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Product addProduct(Product product) {
        System.out.println("Saving product: " + product);
        return repo.save(product);
    }

    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
        // Uncomment and modify if image handling is required
        // product.setImageName(imageFile.getOriginalFilename());
        // product.setImageType(imageFile.getContentType());
        // product.setImageData(imageFile.getBytes());
        return repo.save(product);
    }

    public void deleteProduct(int id) {
        repo.deleteById(id);
    }

    // Uncomment if search functionality is needed
    // public List<Product> SearchProduct(String keyword) {
    //     return repo.SearchProducts(keyword);
    // }
}
