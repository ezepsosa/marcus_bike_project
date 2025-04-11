package com.marcusbike.marcus_bike_api.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.marcusbike.marcus_bike_api.dto.response.ProductPartResponseDTO;
import com.marcusbike.marcus_bike_api.dto.response.ProductResponseDTO;
import com.marcusbike.marcus_bike_api.models.Product;

public class ProductMapper {
	
	public static ProductResponseDTO toDTO(Product product) {
		List<ProductPartResponseDTO> res = product.getProductParts().stream().map(productPart -> ProductPartMapper.toDTO(productPart)).collect(Collectors.toList());
		return ProductResponseDTO.builder().id(product.getId()).productName(product.getProductName()).category(product.getCategory()).material(product.getMaterial()).imageUrl(product.getImageUrl()).productParts(res).createdAt(product.getCreatedAt()).build();
	}

}
