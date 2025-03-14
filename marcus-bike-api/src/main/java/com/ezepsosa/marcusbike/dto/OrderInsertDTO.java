package com.ezepsosa.marcusbike.dto;

import java.util.List;

public record OrderInsertDTO(Long userId, Double finalPrice, List<OrderLineInsertDTO> orderLines) {

}