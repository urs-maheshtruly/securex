package com.ursmahesh.securex.repo;

import com.ursmahesh.securex.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {
    @Override
    Optional<Product> findById(Integer id);

    //    @Query("SELECT p FROM Product p WHERE " +
//            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
//            "LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
//            "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
//            "LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%'))")
//
//    List<Product> SearchProducts(String keyword);
}
