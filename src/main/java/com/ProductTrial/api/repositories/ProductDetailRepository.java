package com.ProductTrial.api.repositories;

import com.ProductTrial.api.entities.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {

    boolean existsByProduct_Id(Long productId);

}