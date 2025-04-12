package com.marcusbike.marcus_bike_api.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductSummaryResponseDTO {
	private Long id;
	private String productName;
	private String brand;
	private String category;
	private String material;
	private String imageUrl;
	private LocalDateTime createdAt;

}
