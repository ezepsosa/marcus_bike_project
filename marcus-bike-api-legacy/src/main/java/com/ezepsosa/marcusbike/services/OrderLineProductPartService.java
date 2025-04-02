package com.ezepsosa.marcusbike.services;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ezepsosa.marcusbike.dto.OrderLineProductPartDTO;
import com.ezepsosa.marcusbike.dto.OrderLineProductPartInsertDTO;
import com.ezepsosa.marcusbike.dto.ProductPartPriceCondition;
import com.ezepsosa.marcusbike.mappers.OrderLineProductPartMapper;
import com.ezepsosa.marcusbike.models.OrderLineProductPart;
import com.ezepsosa.marcusbike.repositories.OrderLineProductPartDAO;
import com.ezepsosa.marcusbike.utils.TransactionHandler;

// Service for managing order line product parts.  
// Handles retrieval, insertion, deletion, and validation of price conditions.

public class OrderLineProductPartService {

    private final OrderLineProductPartDAO orderLineProductPartDAO;
    private final ProductPartService productPartService;
    private final ProductPartConditionService productPartConditionService;

    public OrderLineProductPartService(OrderLineProductPartDAO orderLineProductPartDAO,
            ProductPartConditionService productPartConditionService,
            ProductPartService productPartService) {
        this.orderLineProductPartDAO = orderLineProductPartDAO;
        this.productPartService = productPartService;
        this.productPartConditionService = productPartConditionService;
    }

    public List<OrderLineProductPartDTO> getByOrderLineId(Long orderLineId) {
        return TransactionHandler.startTransaction((connection) -> {
            return orderLineProductPartDAO.getByOrderLineId(connection, orderLineId).stream()
                    .map(OrderLineProductPartMapper::toDTO)
                    .collect(Collectors.toList());
        });
    }

    public List<Long> insertAll(Connection connection, List<OrderLineProductPartInsertDTO> orderLineProductPartsInsert,
            Long orderLineId,
            List<Long> productIds) {

        Map<Long, Double> basePrices = productPartService.getAllPartPriceById(productIds);
        Map<Long, List<ProductPartPriceCondition>> productPartConditions = productPartConditionService
                .getAllById(productIds);
        if (!checkOrderLines(orderLineProductPartsInsert, basePrices, productPartConditions)) {
            return List.of();
        }
        List<OrderLineProductPart> orderLineProductPart = orderLineProductPartsInsert.stream()
                .map(olppdto -> OrderLineProductPartMapper.toModel(olppdto)).collect(Collectors.toList());
        return orderLineProductPartDAO.insertAll(connection, orderLineProductPart, orderLineId);
    }

    /*
     * Given a list of product parts, a price map, and a dependency map,
     * this method retrieves conditions/prices for each order line product part,
     * checks for incompatibilities, and validates the final price.
     */

    private Boolean checkOrderLines(List<OrderLineProductPartInsertDTO> listProductPartDTO,
            Map<Long, Double> basePrices,
            Map<Long, List<ProductPartPriceCondition>> productPartConditions) {

        for (OrderLineProductPartInsertDTO orderLineProductPartDTO : listProductPartDTO) {
            Long productPartId = orderLineProductPartDTO.productPart();
            Double basePriceProduct = basePrices.getOrDefault(productPartId, 0.0);

            List<ProductPartPriceCondition> conditions = productPartConditions.getOrDefault(productPartId, List.of());

            List<ProductPartPriceCondition> matchingConditions = conditions.stream()
                    .filter(condition -> listProductPartDTO.stream()
                            .anyMatch(dto -> dto.productPart().equals(condition.dependant_part_id())))
                    .collect(Collectors.toList());

            if (matchingConditions.stream().anyMatch(ProductPartPriceCondition::condition)) {
                return false;
            }

            double expectedPrice = basePriceProduct + matchingConditions.stream()
                    .mapToDouble(ProductPartPriceCondition::price)
                    .sum();

            if (orderLineProductPartDTO.finalPrice() - expectedPrice > 0) {
                return false;
            }
        }

        return true;
    }

}
