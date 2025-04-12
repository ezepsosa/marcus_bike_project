package com.marcusbike.marcus_bike_api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcusbike.marcus_bike_api.dto.response.ProductDetailResponseDTO;
import com.marcusbike.marcus_bike_api.dto.response.ProductSummaryResponseDTO;
import com.marcusbike.marcus_bike_api.services.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
	
	private final ProductService productService;
	
	@GetMapping
	public ResponseEntity<List<ProductSummaryResponseDTO>> findAllSummary(){
		List<ProductSummaryResponseDTO> response = this.productService.findAllSummary();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
		
	}
	
	@GetMapping("/detailed")
	public ResponseEntity<List<ProductDetailResponseDTO>> findAllDetail(){
		List<ProductDetailResponseDTO> response = this.productService.findAllDetail();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
		
	}
}
