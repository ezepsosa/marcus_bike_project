package com.ezepsosa.marcusbike.mappers;

import java.util.List;

import com.ezepsosa.marcusbike.dto.ProductDTO;
import com.ezepsosa.marcusbike.dto.ProductInsertDTO;
import com.ezepsosa.marcusbike.models.Product;

public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(product.getId(), product.getProductName(), product.getBrand(), product.getCategory(),
                product.getMaterial(), product.getImageUrl());
    }

    public static Product toModel(ProductInsertDTO productInsertDTO) {
        return new Product(productInsertDTO.productName(), productInsertDTO.brand(), productInsertDTO.category(),
                productInsertDTO.material(), productInsertDTO.imageUrl(), List.of());
    }

}
