package com.marcusbike.marcus_bike_api.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.marcusbike.marcus_bike_api.models.ProductPartAssociation;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductDetailResponseDTO {
	private Long id;
	private String productName;
	private String brand;
	private String category;
	private String material;
	private String imageUrl;
	private List<ProductPartResponseDTO> productParts;
	private LocalDateTime createdAt;

}