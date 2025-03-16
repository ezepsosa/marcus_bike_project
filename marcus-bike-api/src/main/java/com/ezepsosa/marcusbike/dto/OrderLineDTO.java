package com.ezepsosa.marcusbike.dto;

public record OrderLineDTO(Long id, Long productId, String productName, Integer quantity) {

}