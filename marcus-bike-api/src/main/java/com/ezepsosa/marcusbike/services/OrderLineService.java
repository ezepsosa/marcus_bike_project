package com.ezepsosa.marcusbike.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ezepsosa.marcusbike.dto.OrderLineDTO;
import com.ezepsosa.marcusbike.dto.OrderLineInsertDTO;
import com.ezepsosa.marcusbike.dto.OrderLineProductPartInsertDTO;
import com.ezepsosa.marcusbike.dto.ProductPartPriceCondition;
import com.ezepsosa.marcusbike.mappers.OrderLineMapper;
import com.ezepsosa.marcusbike.models.OrderLine;
import com.ezepsosa.marcusbike.repositories.OrderLineDAO;

public class OrderLineService {

    private final OrderLineDAO orderLineDAO;
    private final OrderLineProductPartService orderLineProductPartService;
    private final ProductPartService productPartService;
    private final ProductPartConditionService productPartConditionService;

    public OrderLineService(OrderLineDAO orderLineDAO, ProductPartConditionService productPartConditionService,
            ProductPartService productPartService, OrderLineProductPartService orderLineProductPartService) {
        this.orderLineDAO = orderLineDAO;
        this.productPartService = productPartService;
        this.productPartConditionService = productPartConditionService;
        this.orderLineProductPartService = orderLineProductPartService;
    }

    public List<OrderLineDTO> getByOrderId(Long orderId) {
        return orderLineDAO.getByOrderId(orderId).stream().map(OrderLineMapper::toDTO).collect(Collectors.toList());
    }

    public OrderLineDTO getById(Long id) {
        return OrderLineMapper.toDTO(orderLineDAO.getById(id));
    }

    public Map<Long, List<OrderLineDTO>> getAllGroupedByOrder() {
        return orderLineDAO.getAllGroupedByOrder().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                value -> value.getValue().stream().map(OrderLineMapper::toDTO).collect(Collectors.toList())));
    }

    public Long insert(OrderLineInsertDTO orderLineDTO, Long orderId) {

        OrderLine orderLine = OrderLineMapper.toModel(orderLineDTO);
        Long orderLineId = orderLineDAO.insert(orderLine, orderId);
        return orderLineId;
    }

    public Boolean update(OrderLine OrderLine, Long orderId) {
        return orderLineDAO.update(OrderLine, orderId);
    }

    public Boolean delete(Long id) {
        return orderLineDAO.delete(id);
    }

    public Boolean insertAll(List<OrderLineInsertDTO> orderLineInsertDTO, Long orderId) {
        if (orderLineInsertDTO.isEmpty()) {
            return false;
        }
        List<Long> productIds = orderLineInsertDTO.stream()
                .flatMap(orderline -> orderline.orderLineProductParts().stream()
                        .map(orderlineproductpart -> orderlineproductpart.productPart()))
                .collect(Collectors.toList());

        Map<Long, Double> basePrices = productPartService.getAllPartPriceById(productIds);
        Map<Long, ProductPartPriceCondition> productPartConditions = productPartConditionService.getAllById(productIds);

        for (OrderLineInsertDTO oldto : orderLineInsertDTO) {
            OrderLine orderline = OrderLineMapper.toModel(oldto);
            Long orderLineId = insert(oldto, orderId);

            for (OrderLineProductPartInsertDTO ppdto : oldto.orderLineProductParts()) {

            }

            orderLineProductPartService.insertAll(oldto.orderLineProductParts(), orderLineId);

        }

        throw new UnsupportedOperationException("Unimplemented method 'insertAllLines'");
    }

}
