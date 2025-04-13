package com.marcusbike.marcus_bike_api.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.marcusbike.marcus_bike_api.dto.request.ProductRequestDTO;
import com.marcusbike.marcus_bike_api.dto.response.ProductDetailResponseDTO;
import com.marcusbike.marcus_bike_api.dto.response.ProductSummaryResponseDTO;
import com.marcusbike.marcus_bike_api.mappers.ProductMapper;
import com.marcusbike.marcus_bike_api.models.Product;
import com.marcusbike.marcus_bike_api.repositories.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
				.orElseThrow(() -> new EntityNotFoundException(String.format("No product found with id: %d", id)));
		return ProductMapper.toDetailDTO(product);
	}

	@Transactional
	public ProductSummaryResponseDTO create(ProductRequestDTO productDTO) {
		Product product = ProductMapper.toEntity(productDTO);
		Product res = this.productRepository.save(product);
		return ProductMapper.toSummaryDTO(res);

	}

	@Transactional
	public void deleteById(Long id) {
		try {
			this.productRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("No product found with ID " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(
					"Product cannot be deleted because it is being used by another entity");
		}
	}

	@Transactional
	public ProductSummaryResponseDTO update(ProductRequestDTO productDTO, Long id) {
		Product product = this.productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
				String.format("No product found with id: %d, changes cannot be aplied", id)));
		
		product = ProductMapper.updateEntityFromDTO(product, productDTO);
		
		Product res = this.productRepository.save(product);
		return ProductMapper.toSummaryDTO(res);
	}

}
