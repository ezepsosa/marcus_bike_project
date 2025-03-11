package com.ezepsosa.marcusbike.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDTO(Long id, Double finalPrice, LocalDateTime createdAt, List<OrderLineDTO> orderLines) {

}
