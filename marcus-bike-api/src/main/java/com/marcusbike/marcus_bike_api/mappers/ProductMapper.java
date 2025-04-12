package com.marcusbike.marcus_bike_api.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.marcusbike.marcus_bike_api.dto.request.ProductRequestDTO;
import com.marcusbike.marcus_bike_api.dto.response.ProductDetailResponseDTO;
import com.marcusbike.marcus_bike_api.dto.response.ProductPartResponseDTO;
import com.marcusbike.marcus_bike_api.dto.response.ProductSummaryResponseDTO;
import com.marcusbike.marcus_bike_api.models.Product;

public class ProductMapper {

	public static ProductDetailResponseDTO toDetailDTO(Product product) {
		List<ProductPartResponseDTO> productParts = product.getProductPartAssociations().stream()
				.map(productPartAssociation -> ProductPartMapper.toDTO(productPartAssociation.getProductPart()))
				.collect(Collectors.toList());
		return ProductDetailResponseDTO.builder().id(product.getId()).productName(product.getProductName())
				.brand(product.getBrand()).category(product.getCategory()).material(product.getMaterial())
				.productParts(productParts).imageUrl(product.getImageUrl()).createdAt(product.getCreatedAt()).build();
	}

	public static ProductSummaryResponseDTO toSummaryDTO(Product product) {
		return ProductSummaryResponseDTO.builder().id(product.getId()).productName(product.getProductName())
				.brand(product.getBrand()).category(product.getCategory()).material(product.getMaterial())
				.imageUrl(product.getImageUrl()).createdAt(product.getCreatedAt()).build();
	}

	public static Product toEntity(ProductRequestDTO productDTO) {
		return Product.builder().productName(productDTO.getProductName()).brand(productDTO.getBrand())
				.category(productDTO.getCategory()).material(productDTO.getMaterial())
				.imageUrl(productDTO.getImageUrl()).build();
	}

}
