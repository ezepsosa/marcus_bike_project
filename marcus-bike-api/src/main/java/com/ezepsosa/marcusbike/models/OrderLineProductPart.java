package com.ezepsosa.marcusbike.models;

import java.time.LocalDateTime;

public class OrderLineProductPart {

    private OrderLine orderLine;
    private ProductPart productPart;
    private Integer quantity;
    private Double finalPrice;
    private LocalDateTime createdAt;

    public OrderLine getOrderLine() {
        return orderLine;
    }

    public ProductPart getProductPart() {
        return productPart;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public OrderLineProductPart() {
    }

    public OrderLineProductPart(OrderLine orderLine, ProductPart productPart, Integer quantity, Double finalPrice) {
        this.orderLine = orderLine;
        this.productPart = productPart;
        this.quantity = quantity;
        this.finalPrice = finalPrice;
    }

    public OrderLineProductPart(OrderLine orderLine, ProductPart productPart, Integer quantity, Double finalPrice,
            LocalDateTime createdAt) {
        this.orderLine = orderLine;
        this.productPart = productPart;
        this.quantity = quantity;
        this.finalPrice = finalPrice;
        this.createdAt = createdAt;
    }

    // Method for debugging and logs
    @Override
    public String toString() {
        return "OrderLineProductPart [orderLine=" + orderLine + ", productPart=" + productPart + ", quantity="
                + quantity + ", finalPrice=" + finalPrice + ", createdAt=" + createdAt + "]";
    }

}
