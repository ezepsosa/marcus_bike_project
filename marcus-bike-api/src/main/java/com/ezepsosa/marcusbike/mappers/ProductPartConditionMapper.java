package com.ezepsosa.marcusbike.mappers;

import com.ezepsosa.marcusbike.dto.ProductPartConditionDTO;
import com.ezepsosa.marcusbike.dto.ProductPartConditionInsertDTO;
import com.ezepsosa.marcusbike.models.ProductPart;
import com.ezepsosa.marcusbike.models.ProductPartCondition;

public class ProductPartConditionMapper {

    public static ProductPartConditionDTO toDTO(ProductPartCondition productPartCondition) {
        return new ProductPartConditionDTO(productPartCondition.getPartId().getId(),
                productPartCondition.getDependantPartId().getId(),
                productPartCondition.getPriceAdjustment(), productPartCondition.getIsRestriction());
    }

    public static ProductPartCondition toModel(ProductPartConditionInsertDTO productPartCondition) {
        ProductPart firstProductPart = new ProductPart();
        ProductPart secondProductPart = new ProductPart();
        firstProductPart.setId(productPartCondition.partId());
        secondProductPart.setId(productPartCondition.dependantPartId());
        return new ProductPartCondition(firstProductPart, secondProductPart,
                productPartCondition.priceAdjustment(), productPartCondition.isRestriction());
    }

}
