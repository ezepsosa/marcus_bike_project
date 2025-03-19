package com.ezepsosa.marcusbike.services;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ezepsosa.marcusbike.dto.OrderLineDTO;
import com.ezepsosa.marcusbike.dto.OrderLineInsertDTO;
import com.ezepsosa.marcusbike.dto.OrderLineProductPartInsertDTO;
import com.ezepsosa.marcusbike.mappers.OrderLineMapper;
import com.ezepsosa.marcusbike.models.OrderLine;
import com.ezepsosa.marcusbike.repositories.OrderLineDAO;
import com.ezepsosa.marcusbike.utils.TransactionHandler;

// Service for managing order lines.  
// Handles retrieval, insertion, updating, deletion, and validation of order line data.

public class OrderLineService {

    private final OrderLineDAO orderLineDAO;
    private final OrderLineProductPartService orderLineProductPartService;

    public OrderLineService(OrderLineDAO orderLineDAO, OrderLineProductPartService orderLineProductPartService) {
        this.orderLineDAO = orderLineDAO;
        this.orderLineProductPartService = orderLineProductPartService;
    }

    public List<OrderLineDTO> getByOrderId(Long orderId) {
        return TransactionHandler.startTransaction((connection) -> {
            return orderLineDAO.getByOrderId(connection, orderId).stream().map(OrderLineMapper::toDTO)
                    .collect(Collectors.toList());
        });
    }

    public OrderLineDTO getById(Long id) {
        return TransactionHandler.startTransaction((connection) -> {
            return OrderLineMapper.toDTO(orderLineDAO.getById(connection, id));
        });
    }

    public Map<Long, List<OrderLineDTO>> getAllGroupedByOrder() {
        return TransactionHandler.startTransaction((connection) -> {
            return orderLineDAO.getAllGroupedByOrder(connection).entrySet().stream().collect(Collectors.toMap(
                    Map.Entry::getKey,
                    value -> value.getValue().stream().map(OrderLineMapper::toDTO).collect(Collectors.toList())));
        });
    }

    public Long insert(OrderLineInsertDTO orderLineDTO, Long orderId) {
        return TransactionHandler.startTransaction((connection) -> {
            OrderLine orderLine = OrderLineMapper.toModel(orderLineDTO);
            Long orderLineId = orderLineDAO.insert(connection, orderLine, orderId);
            return orderLineId;
        });
    }

    public Boolean update(OrderLine OrderLine, Long orderId) {
        return TransactionHandler.startTransaction((connection) -> {
            return orderLineDAO.update(connection, OrderLine, orderId);
        });
    }

    public Boolean delete(Long id) {
        return TransactionHandler.startTransaction((connection) -> {
            return orderLineDAO.delete(connection, id);
        });
    }

    public List<Long> insertAll(Connection connection, List<OrderLineInsertDTO> orderLineInsertDTO, Long orderId) {
        if (orderLineInsertDTO.isEmpty()) {
            return List.of();
        }
        List<Long> productIds = orderLineInsertDTO.stream()
                .flatMap(orderline -> orderline.orderLineProductParts().stream()
                        .map(OrderLineProductPartInsertDTO::productPart))
                .collect(Collectors.toList());

        List<OrderLine> orderLines = orderLineInsertDTO.stream()
                .map(orderlinedto -> OrderLineMapper.toModel(orderlinedto)).collect(Collectors.toList());
        List<Long> orderLinesInserted = orderLineDAO.insertAll(connection, orderLines, orderId);

        if (orderLinesInserted.size() != orderLines.size()) {
            throw new IllegalArgumentException("Some order lines failed to be inserted");

        }
        for (int index = 0; index < orderLineInsertDTO.size(); index++) {
            OrderLineInsertDTO dto = orderLineInsertDTO.get(index);
            List<Long> orderLinesProductPartsInserted = orderLineProductPartService
                    .insertAll(connection, dto.orderLineProductParts(), orderLinesInserted.get(index), productIds);
            if (orderLinesProductPartsInserted.isEmpty()) {
                throw new IllegalArgumentException("Failed inserting order lines product parts");
            }
        }
        return orderLinesInserted;

    }

}
