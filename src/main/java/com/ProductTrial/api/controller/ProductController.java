package com.ProductTrial.api.controller;

import com.ProductTrial.api.entities.Product;
import com.ProductTrial.api.payloads.ProductSummary;
import com.ProductTrial.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")  //localhost:8080/api/products/create
    public ResponseEntity<Product> createProducts(@RequestBody Product product){
        Product savedProduct = productService.createProduct(product);
        return ResponseEntity.status(201).body(savedProduct);
    }

    @GetMapping("/getAll") //localhost:8080/api/products/getAll
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}") //localhost:8080/api/products/{id}
    public ResponseEntity<Product> getProductsById(@PathVariable long id){
       return ResponseEntity.status(200).body(productService.getProductById(id));
    }
    @GetMapping("/search") //http://localhost:8080/api/products/search?price=200
    public ResponseEntity<List<String>> searchProductNamesByPrice(@RequestParam double price) {
        List<String> productNames = productService.searchProductNamesByPrice(price);
        return ResponseEntity.ok(productNames);
    }

    @GetMapping("/by-category/{category}")  // http://localhost:8080/api/products/by-category/Electronics

    public ResponseEntity<List<ProductSummary>> getProductsByCategory(@PathVariable String category) {
        List<ProductSummary> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/export-excel")  //localhost:8080/api/products/export-excel
    public ResponseEntity<String> exportProductsToExcel() {
        try {
            productService.exportProductsToExcel();
            return ResponseEntity.ok("Excel file exported and saved successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error exporting Excel file: " + e.getMessage());
        }
    }

    @GetMapping("/export-pdf")   //localhost:8080/api/products/export-pdf
    public ResponseEntity<String> exportProductsToPdf() {
        try {
            String filePath = "C:\\Users\\rajpo\\Desktop\\Monika's docs\\project docs/products.pdf"; // Provide the desired file path here
            productService.exportProductsToPdf(filePath);
            return ResponseEntity.ok("PDF file exported and saved: " + filePath);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error exporting PDF file: " + e.getMessage());
        }
    }

    @PutMapping("/update") //localhost:8080/api/products/update?{id}=1
    public ResponseEntity<Product> updateProductsById(@RequestParam Long id, @RequestBody Product product){
      return  ResponseEntity.status(HttpStatus.OK).body(productService.updateProductById(id, product));
    }

    @DeleteMapping("/delete") //localhost:8080/api/products/delete?{id}=1
    public ResponseEntity<String> deleteProduct(@RequestParam Long id){
        productService.deleteProductById(id);
        return  ResponseEntity.status(HttpStatus.OK).body("Record Deleted");
    }




}
