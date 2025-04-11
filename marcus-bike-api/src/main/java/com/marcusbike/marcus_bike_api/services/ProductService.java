package com.marcusbike.marcus_bike_api.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.marcusbike.marcus_bike_api.dto.response.ProductResponseDTO;
import com.marcusbike.marcus_bike_api.mappers.ProductMapper;
import com.marcusbike.marcus_bike_api.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {
	
	private final ProductRepository productRepository;

	public List<ProductResponseDTO> findAll() {
		return this.productRepository.findAll().stream().map(product -> ProductMapper.toDTO(product)).collect(Collectors.toList());
	}

}
