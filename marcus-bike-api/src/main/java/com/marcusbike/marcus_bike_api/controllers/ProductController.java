package com.marcusbike.marcus_bike_api.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.marcusbike.marcus_bike_api.dto.request.ProductRequestDTO;
import com.marcusbike.marcus_bike_api.dto.response.ProductDetailResponseDTO;
import com.marcusbike.marcus_bike_api.dto.response.ProductSummaryResponseDTO;
import com.marcusbike.marcus_bike_api.services.ProductService;
import com.marcusbike.marcus_bike_api.validations.ValidationSequence;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

	private final ProductService productService;

	@GetMapping
	public ResponseEntity<List<ProductSummaryResponseDTO>> findAllSummary() {
		List<ProductSummaryResponseDTO> response = this.productService.findAllSummary();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/detailed")
	public ResponseEntity<List<ProductDetailResponseDTO>> findAllDetail() {
		List<ProductDetailResponseDTO> response = this.productService.findAllDetail();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductDetailResponseDTO> findById(@PathVariable Long id) {
		ProductDetailResponseDTO response = this.productService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		this.productService.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PostMapping
	public ResponseEntity<ProductSummaryResponseDTO> create(@RequestBody @Validated(ValidationSequence.class) ProductRequestDTO productRequest) {
		ProductSummaryResponseDTO response = this.productService.create(productRequest);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId())
				.toUri();
		return ResponseEntity.created(location).body(response);
	}

}
