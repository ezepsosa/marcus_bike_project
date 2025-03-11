package com.ezepsosa.marcusbike.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.ezepsosa.marcusbike.dto.OrderDTO;
import com.ezepsosa.marcusbike.models.Order;
import com.ezepsosa.marcusbike.models.OrderLine;

public class OrderMapper {

    public static OrderDTO toDTO(Order order, List<OrderLine> orderLine) {
        return new OrderDTO(order.getId(), order.getFinalPrice(), order.getCreatedAt(),
                orderLine.stream().map(OrderLineMapper::toDTO).collect(Collectors.toList()));
    }

}
