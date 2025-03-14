package com.ezepsosa.marcusbike.mappers;

import com.ezepsosa.marcusbike.dto.ProductPartDTO;
import com.ezepsosa.marcusbike.dto.ProductPartInsertDTO;
import com.ezepsosa.marcusbike.models.ProductPart;
import com.ezepsosa.marcusbike.models.ProductPartCategory;

public class ProductPartMapper {

        public static ProductPartDTO toDTO(ProductPart productPart) {
                return new ProductPartDTO(productPart.getId(), productPart.getPartOption(), productPart.getBasePrice(),
                                productPart.getCategory().name());
        }

        public static ProductPart toModel(ProductPartInsertDTO productPartInsertDTO) {
                return new ProductPart(productPartInsertDTO.partOption(), productPartInsertDTO.isAvailable(),
                                productPartInsertDTO.basePrice(),
                                ProductPartCategory.valueOf(productPartInsertDTO.productPartCategory().toUpperCase()));
        }
}
