package com.ProductTrial.api.payloads;

import lombok.Data;

@Data
public class ProductSummary {
    private String name;
    private double price;
    private String manufacturer;
    public ProductSummary(String name, double price, String manufacturer) {
        this.name = name;
        this.price = price;
        this.manufacturer = manufacturer;
    }

}
