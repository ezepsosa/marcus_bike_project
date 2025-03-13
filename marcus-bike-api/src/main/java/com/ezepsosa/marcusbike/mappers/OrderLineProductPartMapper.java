package com.ezepsosa.marcusbike.mappers;

import com.ezepsosa.marcusbike.dto.OrderLineProductPartDTO;
import com.ezepsosa.marcusbike.models.OrderLineProductPart;

public class OrderLineProductPartMapper {

        public static OrderLineProductPartDTO toDTO(OrderLineProductPart orderLineProductPart) {
                return new OrderLineProductPartDTO(ProductPartMapper.toDTO(orderLineProductPart.getProductPart()),
                                orderLineProductPart.getFinalPrice());
        }

        public static OrderLineProductPart toDTO(OrderLineProductPartDTO orderLineProductPart) {
                return new OrderLineProductPart();
        }
}
