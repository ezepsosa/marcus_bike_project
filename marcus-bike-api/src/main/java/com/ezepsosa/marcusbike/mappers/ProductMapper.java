package com.ezepsosa.marcusbike.mappers;

import java.util.List;

import com.ezepsosa.marcusbike.dto.ProductDTO;
import com.ezepsosa.marcusbike.dto.ProductInsertDTO;
import com.ezepsosa.marcusbike.models.Product;

public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(product.getId(), product.getProductName());
    }

    public static Product toModel(ProductInsertDTO productInsertDTO) {
        return new Product(productInsertDTO.productName(), List.of());
    }

}
