package com.ezepsosa.marcusbike.models;

import java.time.LocalDateTime;

public class ProductPart {

    private Long id;
    private Product product;
    private String partOption;
    private Boolean isAvailable;
    private Double basePrice;
    private ProductPartCategory category;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public String getPartOption() {
        return partOption;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public ProductPartCategory getCategory() {
        return category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setPartOption(String partOption) {
        this.partOption = partOption;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public void setCategory(ProductPartCategory category) {
        this.category = category;
    }

    public ProductPart() {
    }

    public ProductPart(Long id, Product product, String partOption, Boolean isAvailable, Double basePrice,
            ProductPartCategory category, LocalDateTime createdAt) {
        this.id = id;
        this.product = product;
        this.partOption = partOption;
        this.isAvailable = isAvailable;
        this.basePrice = basePrice;
        this.category = category;
        this.createdAt = createdAt;
    }

    public ProductPart(Product product, String partOption, Boolean isAvailable, Double basePrice,
            ProductPartCategory category) {
        this.product = product;
        this.partOption = partOption;
        this.isAvailable = isAvailable;
        this.basePrice = basePrice;
        this.category = category;
    }

    // Method for debugging and logs
    @Override
    public String toString() {
        return "ProductPart [id=" + id + ", product=" + product + ", partOption=" + partOption + ", isAvailable="
                + isAvailable + ", basePrice=" + basePrice + ", category=" + category + ", createdAt=" + createdAt
                + "]";
    }

}
