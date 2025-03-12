package com.ezepsosa.marcusbike.services;

import java.util.List;
import java.util.stream.Collectors;

import com.ezepsosa.marcusbike.dto.OrderLineDTO;
import com.ezepsosa.marcusbike.mappers.OrderLineMapper;
import com.ezepsosa.marcusbike.models.OrderLine;
import com.ezepsosa.marcusbike.repositories.OrderLineDAO;

public class OrderLineService {

    private final OrderLineDAO orderLineDAO;

    public OrderLineService(OrderLineDAO orderLineDAO) {
        this.orderLineDAO = orderLineDAO;
    }

    public List<OrderLineDTO> getByOrderId(Long orderId) {
        return orderLineDAO.getByOrderId(orderId).stream().map(OrderLineMapper::toDTO).collect(Collectors.toList());
    }

    public OrderLineDTO getById(Long id) {
        return OrderLineMapper.toDTO(orderLineDAO.getById(id));
    }

    public Long insert(OrderLine orderLine, Long orderId) {
        return orderLineDAO.insert(orderLine, orderId);
    }

    public Boolean update(OrderLine OrderLine, Long orderId) {
        return orderLineDAO.update(OrderLine, orderId);
    }

    public Boolean delete(Long id) {
        return orderLineDAO.delete(id);
    }

}
