package com.example.tea_loufu.controller;

import com.example.tea_loufu.entity.Product;
import com.example.tea_loufu.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);


    @PostMapping("/upload")
    public ResponseEntity<Object> uploadProduct(
            @RequestParam("productName") String productName,
            @RequestParam("description") String description,
            @RequestParam("price") BigDecimal price,
            @RequestParam("stock") int stock,
            @RequestParam("file") MultipartFile file) {
        logger.debug("Received productName: {}", productName);
        logger.debug("Received description: {}", description);
        logger.debug("Received price: {}", price);
        logger.debug("Received stock: {}", stock);
        logger.debug("Received file: {}", file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{\"error\": \"File is required\"}");
            }

            // Đường dẫn đến thư mục lưu hình ảnh
            String uploadDir = "D:/2024-2025/HK1/lap trinh web/Project/Tea_Loufu/src/main/resources/static/images/up";

            File uploadDirectory = new File(uploadDir);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs(); // Tạo thư mục nếu chưa tồn tại
            }

            String filePath = Paths.get(uploadDir, file.getOriginalFilename()).toString();
            file.transferTo(new File(filePath));

            // Tạo URL cho hình ảnh
            String imageUrl = "http://localhost:8080/images/" + file.getOriginalFilename();

            if (productName == null || productName.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{\"error\": \"Product Name is required\"}");
            }

            // Lưu sản phẩm vào cơ sở dữ liệu
            Product product = new Product();
            product.setProductName(productName);
            product.setDescription(description);
            product.setPrice(price);
            product.setStock(stock);
            product.setImageUrl(imageUrl); // Lưu URL của hình ảnh

            productService.saveProduct(product);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Product saved successfully!\"}");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"error\": \"Failed to upload file: " + e.getMessage() + "\"}");
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"error\": \"Null value encountered: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"error\": \"An unexpected error occurred: " + e.getMessage() + "\"}");
        }
    }




    @GetMapping("/image/{id}")
    public ResponseEntity<Object> getProductImageUrl(@PathVariable int id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(String.format("{\"error\": \"Product not found for ID: %d\"}", id));
        }

        String imageUrl = product.getImageUrl();
        if (imageUrl == null || imageUrl.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(String.format("{\"error\": \"No image URL found for product ID: %d\"}", id));
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(String.format("{\"imageUrl\": \"%s\"}", imageUrl));
    }


    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable int id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"error\": \"Product not found\"}");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") int productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Product deleted successfully\"}");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"error\": \"Product not found\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"error\": \"An error occurred: " + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/{productID}")
    public ResponseEntity<Object> updateProduct(
            @PathVariable int productID,
            @RequestBody Product updatedProduct) {
        if (updatedProduct == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"error\": \"Product data is required\"}");
        }

        try {
            Product updatedProductResult = productService.updateProduct(productID, updatedProduct);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(updatedProductResult);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"error\": \"Product not found\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"error\": \"An error occurred: " + e.getMessage() + "\"}");
        }
    }
}
