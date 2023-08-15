package com.ProductTrial.api.repositories;

import com.ProductTrial.api.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p.name FROM Product p WHERE p.price = :price")
    List<String> findProductNamesByPrice(double price);

    List<Product> findByCategory(String category);
}


