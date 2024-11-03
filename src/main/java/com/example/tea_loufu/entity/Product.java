package com.example.tea_loufu.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productID;

    @Column(name = "product_name", nullable = false, length = 100)
    private String productName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "image_url", length = 4096)  // Đổi tên từ image_data thành image_url
    private String imageUrl;

    // Getters and Setters

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImageUrl() {  // Cập nhật getter
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {  // Cập nhật setter
        this.imageUrl = imageUrl;
    }
}
