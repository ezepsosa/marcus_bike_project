package com.ezepsosa.marcusbike.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ezepsosa.marcusbike.dto.ProductPartDTO;
import com.ezepsosa.marcusbike.mappers.ProductPartMapper;
import com.ezepsosa.marcusbike.repositories.ProductPartDAO;

public class ProductPartService {

    private final ProductPartDAO productPartDAO;

    public ProductPartService(ProductPartDAO productPartDAO) {
        this.productPartDAO = productPartDAO;
    }

    public List<ProductPartDTO> getAll() {
        return productPartDAO.getAll().stream().map(productPart -> ProductPartMapper.toDTO(productPart))
                .collect(Collectors.toList());

    }

    public ProductPartDTO getById(Long id) {
        return ProductPartMapper.toDTO(productPartDAO.getById(id));
    }

    public Map<Long, Double> getAllPartPrice() {
        return productPartDAO.getAll().stream().map(productPart -> ProductPartMapper.toDTO(productPart))
                .collect(Collectors.toMap(ProductPartDTO::id, ProductPartDTO::basePrice));

    }

    Map<Long, Double> getAllPartPriceById(List<Long> productIds) {
        return productPartDAO.getAllPartPriceById(productIds).stream()
                .map(productPart -> ProductPartMapper.toDTO(productPart))
                .collect(Collectors.toMap(ProductPartDTO::id, ProductPartDTO::basePrice));
    }

}
