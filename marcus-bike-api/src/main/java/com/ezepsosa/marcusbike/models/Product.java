package com.ezepsosa.marcusbike.models;

public class Product {

    private Long id;
    private String productName;

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Product() {
    }

    public Product(Long id, String productName) {
        this.id = id;
        this.productName = productName;
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
