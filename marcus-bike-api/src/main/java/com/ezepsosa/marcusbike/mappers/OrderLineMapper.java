package com.ezepsosa.marcusbike.mappers;

import com.ezepsosa.marcusbike.dto.OrderLineDTO;
import com.ezepsosa.marcusbike.dto.OrderLineInsertDTO;
import com.ezepsosa.marcusbike.models.OrderLine;
import com.ezepsosa.marcusbike.models.Product;

public class OrderLineMapper {

    public static OrderLineDTO toDTO(OrderLine orderLine) {
        return new OrderLineDTO(orderLine.getId(), ProductMapper.toDTO(orderLine.getProduct()),
                orderLine.getQuantity());
    }

    public static OrderLine toModel(OrderLineInsertDTO orderLineInsertDTO) {
        return new OrderLine(new Product(orderLineInsertDTO.productId(), null, null, null),
                orderLineInsertDTO.quantity());
    }

}
