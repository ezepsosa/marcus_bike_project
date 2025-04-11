package com.marcusbike.marcus_bike_api.mappers;

import com.marcusbike.marcus_bike_api.dto.response.ProductPartResponseDTO;
import com.marcusbike.marcus_bike_api.models.ProductPart;

public class ProductPartMapper {

	public static ProductPartResponseDTO toDTO(ProductPart productPart) {
		return ProductPartResponseDTO.builder().id(productPart.getId()).partOption(productPart.getPartOption())
				.stock(productPart.getStock()).basePrice(productPart.getBasePrice())
				.category(productPart.getCategory().name()).createdAt(productPart.getCreatedAt()).build();
	}

}
