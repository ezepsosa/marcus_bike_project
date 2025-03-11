package com.ezepsosa.marcusbike.mappers;

import com.ezepsosa.marcusbike.dto.OrderLineDTO;
import com.ezepsosa.marcusbike.models.OrderLine;

public class OrderLineMapper {

    public static OrderLineDTO toDTO(OrderLine orderLine) {
        return new OrderLineDTO(orderLine.getId(), ProductMapper.toDTO(orderLine.getProduct()),
                orderLine.getQuantity());
    }

}
