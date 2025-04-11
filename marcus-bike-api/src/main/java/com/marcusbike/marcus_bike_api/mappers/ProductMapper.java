package com.marcusbike.marcus_bike_api.mappers;

import com.marcusbike.marcus_bike_api.dto.response.ProductResponseDTO;
import com.marcusbike.marcus_bike_api.models.Product;

public class ProductMapper {
	
	public static ProductResponseDTO toDTO(Product product) {
		return ProductResponseDTO.builder().id(product.getId()).productName(product.getProductName()).category(product.getCategory()).material(product.getMaterial()).imageUrl(product.getImageUrl())
				.createdAt(product.getCreatedAt()).build();
	}

}
