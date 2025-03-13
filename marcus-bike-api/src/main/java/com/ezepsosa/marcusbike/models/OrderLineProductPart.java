package com.ezepsosa.marcusbike.models;

import java.time.LocalDateTime;

public class OrderLineProductPart {

    private OrderLine orderLine;
    private ProductPart productPart;
    private Double finalPrice;
    private LocalDateTime createdAt;

    public OrderLine getOrderLine() {
        return orderLine;
    }

    public ProductPart getProductPart() {
        return productPart;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public OrderLineProductPart() {
    }

    public OrderLineProductPart(OrderLine orderLine, ProductPart productPart, Double finalPrice) {
        this.orderLine = orderLine;
        this.productPart = productPart;
        this.finalPrice = finalPrice;
    }

    public OrderLineProductPart(OrderLine orderLine, ProductPart productPart, Double finalPrice,
            LocalDateTime createdAt) {
        this.orderLine = orderLine;
        this.productPart = productPart;
        this.finalPrice = finalPrice;
        this.createdAt = createdAt;
    }

    // Method for debugging and logs
    @Override
    public String toString() {
        return "OrderLineProductPart [orderLine=" + orderLine + ", productPart=" + productPart + ", finalPrice="
                + finalPrice + ", createdAt=" + createdAt + "]";
    }

}
