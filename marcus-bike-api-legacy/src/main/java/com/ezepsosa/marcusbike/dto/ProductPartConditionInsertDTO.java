package com.ezepsosa.marcusbike.dto;

public record ProductPartConditionInsertDTO(Long partId, Long dependantPartId, Double priceAdjustment,
        Boolean isRestriction) {

}
