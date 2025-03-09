package com.ezepsosa.marcusbike.models;

import java.time.LocalDateTime;

public class Product {

    private Long id;
    private String productName;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Product() {
    }

    public Product(Long id, String productName, LocalDateTime createdAt) {
        this.id = id;
        this.productName = productName;
        this.createdAt = createdAt;
    }

    public Product(String productName) {
        this.productName = productName;
    }

    // Method for debugging and logs
    @Override
    public String toString() {
        return "Product [id=" + id + ", productName=" + productName + "]";
    }

}
