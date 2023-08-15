package com.ProductTrial.api.service;

import com.ProductTrial.api.entities.Product;
import com.ProductTrial.api.payloads.ProductSummary;
import com.ProductTrial.api.repositories.ProductRepository;
import com.ProductTrial.api.utils.EmailService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final EmailService emailService;
    private final String excelExportPath;


    @Autowired
    public ProductService(ProductRepository productRepository, EmailService emailService,@Value("${excel.export.path}") String excelExportPath) {
        this.productRepository = productRepository;
        this.emailService = emailService;
        this.excelExportPath = excelExportPath;

    }
    public Product createProduct(Product product){
        Product savedProduct = productRepository.save(product);

        // Send an email notification
        String to = savedProduct.getEmail();
        String subject = "New Product Added";
        String text = "A new product has been added: " + savedProduct.getName();
        emailService.sendEmail(to, subject, text);

        return savedProduct;
    }


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public byte[] exportProductsToExcel() throws IOException {
        List<Product> products = getAllProducts();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Products");
            int rowNum = 0;

            for (Product product : products) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(product.getName());
                row.createCell(1).setCellValue(product.getPrice());
                row.createCell(2).setCellValue(product.getCategory());
                row.createCell(3).setCellValue(product.getEmail());
                row.createCell(4).setCellValue(product.getManufacturer());
                row.createCell(5).setCellValue(product.getStockQuantity());
                // Add more columns as needed
            }
            String filePath = excelExportPath + "/products.xlsx";
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }

    }
    public void exportProductsToPdf(String filePath) throws DocumentException, IOException {
        List<Product> products = getAllProducts();

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        PdfPTable table = new PdfPTable(6); // Number of columns
        table.addCell("Product Name");
        table.addCell("Product Price");
        table.addCell("Product Category");
        table.addCell("Product StockQuantity");
        table.addCell("Product Manufacturer");
        table.addCell("Product email");

        for (Product product : products) {
            table.addCell(product.getName());
            table.addCell(String.valueOf(product.getPrice()));
            table.addCell(product.getCategory());
            table.addCell(String.valueOf(product.getStockQuantity()));
            table.addCell(product.getManufacturer());
            table.addCell(product.getEmail());
        }

        document.add(table);
        document.close();
    }

    public Product getProductById(long id){
        return productRepository.findById(id).get();
    }


    public Product updateProductById(long id, Product product){
        Product newProduct = productRepository.findById(id).get();
        product.setId(id);
        return productRepository.save(product);
    }

    public void deleteProductById(long id){
        productRepository.deleteById(id);
    }

    public List<String> searchProductNamesByPrice(double price) {
        return productRepository.findProductNamesByPrice(price);
    }

    public List<ProductSummary> getProductsByCategory(String category) {
        List<Product> products = productRepository.findByCategory(category);

        return products.stream()
                .map(product -> new ProductSummary(product.getName(), product.getPrice(), product.getManufacturer()))
                .collect(Collectors.toList());
    }


}
