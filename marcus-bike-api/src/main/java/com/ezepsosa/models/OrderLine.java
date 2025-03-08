package com.ezepsosa.models;

public class OrderLine {

    private Long id;
    private Order order;
    private Product product;
    private Integer quantity;
    private Double price;

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

    public Double getPrice() {
        return price;
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

    public void setPrice(Double price) {
        this.price = price;
    }

    public OrderLine() {
    }

    public OrderLine(Long id, Order order, Product product, Integer quantity, Double price) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderLine(Order order, Product product, Integer quantity, Double price) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    // Method for debugging and logs
    @Override
    public String toString() {
        return "OrderLine [id=" + id + ", order=" + order + ", product=" + product + ", quantity=" + quantity
                + ", price=" + price + "]";
    }

}
