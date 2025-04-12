package com.marcusbike.marcus_bike_api.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.marcusbike.marcus_bike_api.dto.response.ProductDetailResponseDTO;
import com.marcusbike.marcus_bike_api.dto.response.ProductSummaryResponseDTO;
import com.marcusbike.marcus_bike_api.mappers.ProductMapper;
import com.marcusbike.marcus_bike_api.models.Product;
import com.marcusbike.marcus_bike_api.repositories.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {

	private final ProductRepository productRepository;

	public List<ProductSummaryResponseDTO> findAllSummary() {
		return this.productRepository.findAll().stream().map(product -> ProductMapper.toSummaryDTO(product))
				.collect(Collectors.toList());
	}

	public List<ProductDetailResponseDTO> findAllDetail() {
		return this.productRepository.findAll().stream().map(product -> ProductMapper.toDetailDTO(product))
				.collect(Collectors.toList());
	}

	public ProductDetailResponseDTO findById(Long id) {
		Product product = this.productRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("\"No product found with id: {}\", id"));
		return ProductMapper.toDetailDTO(product);
	}

}
