package com.marcusbike.marcus_bike_api.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductPartResponseDTO {
	private Long id;
	private String partOption;
	private Double stock;
	private Double basePrice;
	private String category;
	private LocalDateTime createdAt;
}
