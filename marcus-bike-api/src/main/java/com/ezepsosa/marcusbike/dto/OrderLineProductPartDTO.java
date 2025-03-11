package com.ezepsosa.marcusbike.dto;

public record OrderLineProductPartDTO(ProductPartDTO productPart, Integer quantity, Double finalPrice) {

}
