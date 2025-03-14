package com.ezepsosa.marcusbike.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ezepsosa.marcusbike.dto.OrderLineProductPartDTO;
import com.ezepsosa.marcusbike.dto.OrderLineProductPartInsertDTO;
import com.ezepsosa.marcusbike.dto.ProductPartPriceCondition;
import com.ezepsosa.marcusbike.mappers.OrderLineProductPartMapper;
import com.ezepsosa.marcusbike.models.OrderLineProductPart;
import com.ezepsosa.marcusbike.repositories.OrderLineProductPartDAO;

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
        return orderLineProductPartDAO.getByOrderLineId(orderLineId).stream().map(OrderLineProductPartMapper::toDTO)
                .collect(Collectors.toList());
    }

    public OrderLineProductPartDTO getByOrderId(Long orderLine, Long dependantPart) {
        return OrderLineProductPartMapper.toDTO(orderLineProductPartDAO.getById(orderLine, dependantPart));
    }

    public Boolean delete(Long orderLine, Long dependantPart) {
        return orderLineProductPartDAO.delete(orderLine, dependantPart);
    }

    public List<Long> insertAll(List<OrderLineProductPartInsertDTO> orderLineProductPartsInsert, Long orderLineId,
            List<Long> productIds) {

        Map<Long, Double> basePrices = productPartService.getAllPartPriceById(productIds);
        Map<Long, List<ProductPartPriceCondition>> productPartConditions = productPartConditionService
                .getAllById(productIds);
        if (!checkOrderLines(orderLineProductPartsInsert, basePrices, productPartConditions)) {
            return List.of();
        }
        List<OrderLineProductPart> orderLineProductPart = orderLineProductPartsInsert.stream()
                .map(olppdto -> OrderLineProductPartMapper.toModel(olppdto)).collect(Collectors.toList());
        return orderLineProductPartDAO.insertAll(orderLineProductPart, orderLineId);
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
