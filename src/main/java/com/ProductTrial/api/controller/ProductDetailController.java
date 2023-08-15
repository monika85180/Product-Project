package com.ProductTrial.api.controller;

import com.ProductTrial.api.entities.Product;
import com.ProductTrial.api.entities.ProductDetail;
import com.ProductTrial.api.service.ProductDetailService;
import com.ProductTrial.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/product-details")
public class ProductDetailController {
    private final ProductDetailService productDetailService;
    private final ProductService productService; // Inject ProductService

    @Autowired
    public ProductDetailController(ProductDetailService productDetailService, ProductService productService) {
        this.productDetailService = productDetailService;
        this.productService = productService;
    }

    @PostMapping("/associate/{productId}") // localhost:8080/api/product-details/associate/{productId}
    public ResponseEntity<ProductDetail> createProductDetailAndAssociate(@PathVariable Long productId, @RequestBody ProductDetail productDetail) {
        Product product = productService.getProductById(productId);

        if (product != null) {
            productDetail.setProduct(product);
            ProductDetail createdProductDetail = productDetailService.createProductDetail(productDetail);
            return ResponseEntity.created(null).body(createdProductDetail);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}