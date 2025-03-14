package com.ezepsosa.marcusbike.dto;

public record ProductPartConditionDTO(Long partId, Long dependantPartId, Double priceAdjustment,
        Boolean isRestriction) {

}
