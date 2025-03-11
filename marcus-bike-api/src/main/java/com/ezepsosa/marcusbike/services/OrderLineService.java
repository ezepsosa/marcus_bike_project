package com.ezepsosa.marcusbike.services;

import java.util.List;
import java.util.stream.Collectors;

import com.ezepsosa.marcusbike.dto.OrderLineDTO;<<<<<<<HEAD
import com.ezepsosa.marcusbike.dto.OrderLineProductPartDTO;
import com.ezepsosa.marcusbike.mappers.OrderLineMapper;
import com.ezepsosa.marcusbike.mappers.OrderLineProductPartMapper;=======
import com.ezepsosa.marcusbike.mappers.OrderLineMapper;>>>>>>>7d 4ea dd(Add order controller and removed OrderLine controller.Add service and routes for Order)
import com.ezepsosa.marcusbike.models.OrderLine;
import com.ezepsosa.marcusbike.repositories.OrderLineDAO;
import com.ezepsosa.marcusbike.repositories.OrderLineProductPartDAO;

public class OrderLineService {

    private final OrderLineDAO orderLineDAO;
    private final OrderLineProductPartDAO orderLineProductPartDAO;

    public OrderLineService(OrderLineDAO orderLineDAO, OrderLineProductPartDAO orderLineProductPartDAO) {
        this.orderLineDAO = orderLineDAO;
        this.orderLineProductPartDAO = orderLineProductPartDAO;
    }

    public List<OrderLineProductPartDTO> getByOrderLineId(Long orderLineId) {
        return orderLineProductPartDAO.getByOrderLineId(orderLineId).stream().map(OrderLineProductPartMapper::toDTO)
                .collect(Collectors.toList());
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
