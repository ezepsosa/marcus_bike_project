package com.ezepsosa.marcusbike.mappers;

import com.ezepsosa.marcusbike.dto.ProductPartDTO;
import com.ezepsosa.marcusbike.models.ProductPart;

public class ProductPartMapper {

        public static ProductPartDTO toDTO(ProductPart productPart) {
                return new ProductPartDTO(productPart.getId(), productPart.getPartOption(), productPart.getBasePrice(),
                                productPart.getCategory().name());
        }
}
