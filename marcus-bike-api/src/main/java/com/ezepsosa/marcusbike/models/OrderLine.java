package com.ezepsosa.marcusbike.models;

import java.time.LocalDateTime;

public class OrderLine {

    private Long id;
    private Order order;
    private Product product;
    private Integer quantity;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
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

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public OrderLine() {
    }

    public OrderLine(Long id, Order order, Product product, Integer quantity, LocalDateTime createdAt) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    public OrderLine(Order order, Product product, Integer quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    // Method for debugging and logs
    @Override
    public String toString() {
        return "OrderLine [id=" + id + ", order=" + order + ", product=" + product + ", quantity=" + quantity
                + ", price=" + createdAt + "]";
    }

}
