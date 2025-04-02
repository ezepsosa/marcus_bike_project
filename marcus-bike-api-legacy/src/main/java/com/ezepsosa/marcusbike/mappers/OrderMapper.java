package com.ezepsosa.marcusbike.mappers;

import java.util.ArrayList;
import java.util.List;

import com.ezepsosa.marcusbike.dto.OrderDTO;
import com.ezepsosa.marcusbike.dto.OrderInsertDTO;
import com.ezepsosa.marcusbike.dto.OrderLineDTO;
import com.ezepsosa.marcusbike.models.Order;
import com.ezepsosa.marcusbike.models.User;

public class OrderMapper {

    public static OrderDTO toDTO(Order order, List<OrderLineDTO> orderLine) {
        return new OrderDTO(order.getId(), order.getFinalPrice(), order.getCreatedAt(), orderLine);
    }

    public static Order toModel(OrderInsertDTO orderInsertDTO) {
        return new Order(new User(orderInsertDTO.userId(), null, null, null, null, null), orderInsertDTO.finalPrice(),
                new ArrayList<>());
    }

}
