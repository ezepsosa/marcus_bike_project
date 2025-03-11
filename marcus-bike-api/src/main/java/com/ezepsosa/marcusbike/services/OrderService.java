package com.ezepsosa.marcusbike.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ezepsosa.marcusbike.dto.OrderDTO;
import com.ezepsosa.marcusbike.mappers.OrderMapper;
import com.ezepsosa.marcusbike.models.Order;
import com.ezepsosa.marcusbike.models.OrderLine;
import com.ezepsosa.marcusbike.repositories.OrderDAO;
import com.ezepsosa.marcusbike.repositories.OrderLineDAO;

public class OrderService {

    private final OrderDAO orderDAO;
    private final OrderLineDAO orderLineDAO;

    public OrderService(OrderDAO orderDAO, OrderLineDAO orderLineDAO) {
        this.orderDAO = orderDAO;
        this.orderLineDAO = orderLineDAO;
    }

    public List<OrderDTO> getAll() {
        Map<Long, List<OrderLine>> orderLinesMap = orderLineDAO.getAllGroupedByOrder();
        return orderDAO.getAll().stream()
                .map(order -> OrderMapper.toDTO(order, orderLinesMap.getOrDefault(order.getId(), new ArrayList<>())))
                .collect(Collectors.toList());

    }

    public OrderDTO getById(Long id) {
        List<OrderLine> orderLines = orderLineDAO.getByOrderId(id);
        System.out.println(orderLines);
        return OrderMapper.toDTO(orderDAO.getById(id), orderLines);
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
