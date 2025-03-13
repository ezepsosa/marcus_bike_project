package com.ezepsosa.marcusbike.services;

import java.util.List;
import java.util.stream.Collectors;

import com.ezepsosa.marcusbike.dto.OrderLineProductPartDTO;
import com.ezepsosa.marcusbike.dto.OrderLineProductPartInsertDTO;
import com.ezepsosa.marcusbike.mappers.OrderLineProductPartMapper;
import com.ezepsosa.marcusbike.repositories.OrderLineProductPartDAO;

public class OrderLineProductPartService {

    private final OrderLineProductPartDAO orderLineProductPartDAO;

    public OrderLineProductPartService(OrderLineProductPartDAO orderLineProductPartDAO) {
        this.orderLineProductPartDAO = orderLineProductPartDAO;
    }

    public List<OrderLineProductPartDTO> getByOrderLineId(Long orderLineId) {
        return orderLineProductPartDAO.getByOrderLineId(orderLineId).stream().map(OrderLineProductPartMapper::toDTO)
                .collect(Collectors.toList());
    }

    public OrderLineProductPartDTO getByOrderId(Long orderLine, Long dependantPart) {
        return OrderLineProductPartMapper.toDTO(orderLineProductPartDAO.getById(orderLine, dependantPart));
    }

    public Boolean delete(Long orderLine, Long dependantPart) {
        return orderLineProductPartDAO.delete(orderLine, dependantPart);
    }

    void insertAll(List<OrderLineProductPartInsertDTO> orderLineProductParts, Long orderLineId) {

    }

}
