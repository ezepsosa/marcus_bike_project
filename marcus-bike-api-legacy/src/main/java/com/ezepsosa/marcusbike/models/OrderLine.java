package com.ezepsosa.marcusbike.models;

import java.time.LocalDateTime;

public class OrderLine {

    private Long id;
    private Product product;
    private Integer quantity;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public OrderLine() {
    }

    public OrderLine(Long id, Product product, Integer quantity, LocalDateTime createdAt) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    public OrderLine(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Method for debugging and logs
    @Override
    public String toString() {
        return "OrderLine [id=" + id + ", product=" + product + ", quantity=" + quantity
                + ", price=" + createdAt + "]";
    }

}
