package com.ezepsosa.marcusbike.mappers;

import java.util.stream.Collectors;

import com.ezepsosa.marcusbike.dto.OrderDTO;
import com.ezepsosa.marcusbike.models.Order;

public class OrderMapper {

    public static OrderDTO toDTO(Order order) {
        return new OrderDTO(order.getId(), order.getFinalPrice(), order.getCreatedAt(),
                order.getOrderLines().stream().map(orderLine -> OrderLineMapper.toDTO(orderLine))
                        .collect(Collectors.toList()));
    }

}
//