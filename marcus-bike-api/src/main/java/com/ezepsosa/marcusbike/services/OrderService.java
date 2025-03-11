package com.ezepsosa.marcusbike.services;

import java.util.List;

import com.ezepsosa.marcusbike.models.Order;
import com.ezepsosa.marcusbike.repositories.OrderDAO;

public class OrderService {

    private OrderDAO orderDAO;

    public OrderService(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public List<Order> getAll() {
        return orderDAO.getAll();
    }

    public Order getById(Long id) {
        return orderDAO.getById(id);
    }

    public Long insert(Order orderLine) {
        return orderDAO.insert(orderLine);
    }

    public Boolean update(Order OrderLine) {
        return orderDAO.update(OrderLine);
    }

    public Boolean delete(Long id) {
        return orderDAO.delete(id);
    }

}
