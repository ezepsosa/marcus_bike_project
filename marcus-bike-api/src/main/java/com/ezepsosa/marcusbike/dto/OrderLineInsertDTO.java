package com.ezepsosa.marcusbike.dto;

import java.util.List;

public record OrderLineInsertDTO(Long productId, Integer quantity,
        List<OrderLineProductPartInsertDTO> orderLineProductParts) {

}
