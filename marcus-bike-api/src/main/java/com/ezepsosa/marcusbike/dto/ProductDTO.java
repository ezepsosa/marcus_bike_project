package com.ezepsosa.marcusbike.dto;

import java.time.LocalDateTime;

public record ProductDTO(Long id, String productName, LocalDateTime createdAt) {

}
