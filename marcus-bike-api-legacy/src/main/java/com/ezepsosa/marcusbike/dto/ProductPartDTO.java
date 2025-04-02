package com.ezepsosa.marcusbike.dto;

public record ProductPartDTO(Long id, String partOption, Double basePrice, Boolean isAvailable,
        String productPartCategory) {

}
