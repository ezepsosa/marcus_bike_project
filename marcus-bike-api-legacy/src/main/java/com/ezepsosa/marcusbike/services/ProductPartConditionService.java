package com.ezepsosa.marcusbike.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ezepsosa.marcusbike.dto.ProductPartConditionDTO;
import com.ezepsosa.marcusbike.dto.ProductPartConditionInsertDTO;
import com.ezepsosa.marcusbike.dto.ProductPartPriceCondition;
import com.ezepsosa.marcusbike.mappers.ProductPartConditionMapper;
import com.ezepsosa.marcusbike.repositories.ProductPartConditionDAO;
import com.ezepsosa.marcusbike.utils.TransactionHandler;

// Service for managing product part conditions.  
// Handles retrieval, insertion, deletion, and validation of product part dependencies and price adjustments.

public class ProductPartConditionService {

    private final ProductPartConditionDAO productPartConditionDAO;

    public ProductPartConditionService(ProductPartConditionDAO productPartConditionDAO) {
        this.productPartConditionDAO = productPartConditionDAO;
    }

    public List<ProductPartConditionDTO> getAll() {
        return TransactionHandler.startTransaction((connection) -> {

            return productPartConditionDAO.getAll(connection).stream()
                    .map(productPartCondition -> ProductPartConditionMapper.toDTO(productPartCondition))
                    .collect(Collectors.toList());
        });
    }

    public Map<Long, List<ProductPartPriceCondition>> getAllById(List<Long> productIds) {
        return TransactionHandler.startTransaction((connection) -> {
            return productPartConditionDAO.getAllById(connection, productIds).stream()
                    .collect(Collectors.groupingBy(
                            condition -> condition.getPartId().getId(),
                            Collectors.mapping(
                                    condition -> new ProductPartPriceCondition(
                                            condition.getDependantPartId().getId(),
                                            condition.getPriceAdjustment(),
                                            condition.getIsRestriction()),
                                    Collectors.toList())));
        });
    }

    public boolean delete(Long productpartid, Long dependantproductpartid) {
        return TransactionHandler.startTransaction((connection) -> {
            return productPartConditionDAO.delete(connection, productpartid, dependantproductpartid);
        });
    }

    public boolean insert(ProductPartConditionInsertDTO productPartInsertDTO) {
        return TransactionHandler.startTransaction((connection) -> {
            return productPartConditionDAO.insert(connection, ProductPartConditionMapper.toModel(productPartInsertDTO));
        });
    }
}
