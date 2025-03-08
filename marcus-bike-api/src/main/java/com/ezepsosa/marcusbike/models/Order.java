package com.ezepsosa.marcusbike.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order {

    private Long id;
    private Integer appUserId;
    private LocalDateTime creationDate;
    private Double totalPrice;
    private ArrayList<OrderLine> orderLines;

    public Long getId() {
        return id;
    }

    public Integer getAppUserId() {
        return appUserId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public ArrayList<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setOrderLines(ArrayList<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public Order() {
    }

    public Order(Long id, Integer appUserId, LocalDateTime creationDate, Double totalPrice,
            ArrayList<OrderLine> orderLines) {
        this.id = id;
        this.appUserId = appUserId;
        this.creationDate = creationDate;
        this.totalPrice = totalPrice;
        this.orderLines = orderLines;
    }

    public Order(Integer appUserId, Double totalPrice,
            ArrayList<OrderLine> orderLines) {
        this.appUserId = appUserId;
        this.totalPrice = totalPrice;
        this.orderLines = orderLines;
    }

    // Method for debugging and logs
    @Override
    public String toString() {
        return "Order [id=" + id + ", appUserId=" + appUserId + ", creationDate=" + creationDate + ", totalPrice="
                + totalPrice + ", orderLines=" + orderLines + "]";
    }

}
