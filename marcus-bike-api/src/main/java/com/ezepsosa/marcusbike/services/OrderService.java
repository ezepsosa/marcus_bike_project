package com.ezepsosa.marcusbike.services;

import java.util.List;
import java.util.stream.Collectors;

import com.ezepsosa.marcusbike.dto.OrderDTO;
import com.ezepsosa.marcusbike.mappers.OrderMapper;
import com.ezepsosa.marcusbike.models.Order;
import com.ezepsosa.marcusbike.repositories.OrderDAO;

public class OrderService {

    private OrderDAO orderDAO;

    public OrderService(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public List<OrderDTO> getAll() {
        return orderDAO.getAll().stream().map(order -> OrderMapper.toDTO(order)).collect(Collectors.toList());
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
