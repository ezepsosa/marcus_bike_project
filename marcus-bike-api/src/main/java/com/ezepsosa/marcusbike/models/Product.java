package com.ezepsosa.marcusbike.models;

import java.time.LocalDateTime;
import java.util.List;

public class Product {

    private Long id;
    private String productName;
    private LocalDateTime createdAt;
    private List<ProductPart> productParts;

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<ProductPart> getProductParts() {
        return productParts;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductParts(List<ProductPart> productParts) {
        this.productParts = productParts;
    }

    public Product() {
    }

    public Product(Long id, String productName, List<ProductPart> productParts, LocalDateTime createdAt) {
        this.id = id;
        this.productName = productName;
        this.productParts = productParts;
        this.createdAt = createdAt;
    }

    public Product(String productName, List<ProductPart> productParts) {
        this.productName = productName;
        this.productParts = productParts;
    }

    // Method for debugging and logs
    @Override
    public String toString() {
        return "Product [id=" + id + ", productName=" + productName + ", createdAt=" + createdAt + ", productParts="
                + productParts + "]";
    }

}
