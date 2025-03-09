package com.ezepsosa.marcusbike.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order {

    private Long id;
    private User user;
    private Double finalPrice;
    private LocalDateTime createdAt;
    private ArrayList<OrderLine> orderLines;

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public ArrayList<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public void setOrderLines(ArrayList<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public Order() {
    }

    public Order(Long id, User user, Double finalPrice,
            ArrayList<OrderLine> orderLines, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.createdAt = createdAt;
        this.finalPrice = finalPrice;
        this.orderLines = orderLines;
    }

    public Order(User user, Double finalPrice,
            ArrayList<OrderLine> orderLines) {
        this.user = user;
        this.finalPrice = finalPrice;
        this.orderLines = orderLines;
    }

    // Method for debugging and logs
    @Override
    public String toString() {
        return "Order [id=" + id + ", appUserId=" + user + ", creationDate=" + createdAt + ", totalPrice="
                + finalPrice + ", orderLines=" + orderLines + "]";
    }

}
