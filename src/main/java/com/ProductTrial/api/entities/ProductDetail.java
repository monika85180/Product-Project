package com.ProductTrial.api.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String color;
    private String size;
    private String material;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    // Constructors, getters, setters
}

