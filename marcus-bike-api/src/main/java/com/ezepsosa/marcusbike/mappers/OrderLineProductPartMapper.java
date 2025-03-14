package com.ezepsosa.marcusbike.mappers;

import com.ezepsosa.marcusbike.dto.OrderLineProductPartDTO;
import com.ezepsosa.marcusbike.dto.OrderLineProductPartInsertDTO;
import com.ezepsosa.marcusbike.models.OrderLineProductPart;
import com.ezepsosa.marcusbike.models.ProductPart;

public class OrderLineProductPartMapper {

        public static OrderLineProductPartDTO toDTO(OrderLineProductPart orderLineProductPart) {
                return new OrderLineProductPartDTO(ProductPartMapper.toDTO(orderLineProductPart.getProductPart()),
                                orderLineProductPart.getFinalPrice());
        }

        public static OrderLineProductPart toModel(OrderLineProductPartInsertDTO orderLineProductPartDTO) {
                return new OrderLineProductPart(null,
                                new ProductPart(orderLineProductPartDTO.productPart(), null, null, null, null, null),
                                orderLineProductPartDTO.finalPrice());
        }
}
