package com.ezepsosa.marcusbike.services;

import java.util.List;

import com.ezepsosa.marcusbike.models.OrderLine;
import com.ezepsosa.marcusbike.repositories.OrderLineDAO;

public class OrderLineService {

    private OrderLineDAO orderLineDAO;

    public OrderLineService(OrderLineDAO orderLineDAO) {
        this.orderLineDAO = orderLineDAO;
    }

    public List<OrderLine> getAll() {
        return orderLineDAO.getAll();
    }

    public OrderLine getById(Long id) {
        return orderLineDAO.getById(id);
    }

    public Long insert(OrderLine orderLine) {
        return orderLineDAO.insert(orderLine);
    }

    public Boolean update(OrderLine OrderLine) {
        return orderLineDAO.update(OrderLine);
    }

    public Boolean delete(Long id) {
        return orderLineDAO.delete(id);
    }

}
