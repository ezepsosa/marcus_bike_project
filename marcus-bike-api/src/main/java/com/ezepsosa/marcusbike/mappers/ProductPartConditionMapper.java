package com.ezepsosa.marcusbike.mappers;

import com.ezepsosa.marcusbike.dto.ProductPartConditionDTO;
import com.ezepsosa.marcusbike.models.ProductPartCondition;

public class ProductPartConditionMapper {

    public static ProductPartConditionDTO toDTO(ProductPartCondition productPartCondition) {
        return new ProductPartConditionDTO(productPartCondition.getPartId().getId(),
                productPartCondition.getDependantPartId().getId(),
                productPartCondition.getPriceAdjustment(), productPartCondition.getIsRestriction());
    }

}
