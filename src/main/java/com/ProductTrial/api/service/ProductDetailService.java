package com.ProductTrial.api.service;

import com.ProductTrial.api.entities.ProductDetail;
import com.ProductTrial.api.exceptions.ProductDetailAlreadyExistsException;
import com.ProductTrial.api.repositories.ProductDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProductDetailService {
    private final ProductDetailRepository productDetailRepository;

    @Autowired
    public ProductDetailService(ProductDetailRepository productDetailRepository) {
        this.productDetailRepository = productDetailRepository;
    }

    @Transactional
    public ProductDetail createProductDetail(ProductDetail productDetail) {
        Long productId = productDetail.getProduct().getId();

        // Check if a ProductDetail already exists for the specified Product ID
        if (productDetailRepository.existsByProduct_Id(productId)) {
            throw new ProductDetailAlreadyExistsException("A ProductDetail already exists for the specified Product ID.");
        }

        // If validation passes, proceed with creating the ProductDetail
        return productDetailRepository.save(productDetail);
    }

    }
