package com.ezepsosa.marcusbike.models;

import java.time.LocalDateTime;
import java.util.List;

public class Product {

    private Long id;
    private String productName;
    private String brand;
    private String category;
    private String material;
    private String imageUrl;
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

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return category;
    }

    public String getMaterial() {
        return material;
    }

    public String getImageUrl() {
        return imageUrl;
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

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Product(Long id) {
        this.id = id;
    }

    public Product(Long id, String productName, String brand, String category, String material, String imageUrl,
            List<ProductPart> productParts, LocalDateTime createdAt) {
        this.id = id;
        this.productName = productName;
        this.brand = brand;
        this.category = category;
        this.material = material;
        this.imageUrl = imageUrl;
        this.productParts = productParts;
        this.createdAt = createdAt;

    }

    public Product(String productName, String brand, String category, String material, String imageUrl,
            List<ProductPart> productParts) {
        this.productName = productName;
        this.productParts = productParts;
        this.brand = brand;
        this.category = category;
        this.material = material;
        this.imageUrl = imageUrl;
    }

    // Method for debugging and logs
    @Override
    public String toString() {
        return "Product [id=" + id + ", productName=" + productName + ", createdAt=" + createdAt + ", productParts="
                + productParts + "]";
    }

}
