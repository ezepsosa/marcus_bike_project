package com.ezepsosa.marcusbike.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ezepsosa.marcusbike.dto.OrderDTO;
import com.ezepsosa.marcusbike.dto.OrderInsertDTO;
import com.ezepsosa.marcusbike.dto.OrderLineDTO;
import com.ezepsosa.marcusbike.mappers.OrderMapper;
import com.ezepsosa.marcusbike.models.Order;
import com.ezepsosa.marcusbike.repositories.OrderDAO;
import com.ezepsosa.marcusbike.utils.TransactionHandler;

// Service for managing orders.  
// Handles retrieval, insertion, updating, and deletion of orders, ensuring price validation.
public class OrderService {

    private final OrderDAO orderDAO;
    private final OrderLineService orderLineService;

    public OrderService(OrderDAO orderDAO, OrderLineService orderLineService) {
        this.orderDAO = orderDAO;
        this.orderLineService = orderLineService;
    }

    public List<OrderDTO> getAll() {
        return TransactionHandler.startTransaction((connection) -> {
            Map<Long, List<OrderLineDTO>> orderLinesMap = orderLineService.getAllGroupedByOrder();
            return orderDAO.getAll(connection).stream()
                    .map(order -> OrderMapper.toDTO(order,
                            orderLinesMap.getOrDefault(order.getId(), orderLinesMap.get(order.getId()))))
                    .collect(Collectors.toList());
        });

    }

    public OrderDTO getById(Long id) {
        return TransactionHandler.startTransaction((connection) -> {
            List<OrderLineDTO> orderLines = orderLineService.getByOrderId(id);
            Order order = orderDAO.getById(connection, id);
            if (order != null) {
                return OrderMapper.toDTO(order, orderLines);
            } else {
                return null;
            }
        });
    }

    public Long insert(OrderInsertDTO orderDTO) {
        return TransactionHandler.startTransaction((connection) -> {
            double epsilon = 0.000001d;

            Double finalPriceToCheck = orderDTO.finalPrice();
            Double sumFinalPrice = orderDTO.orderLines().stream()
                    .mapToDouble(orderLine -> orderLine.orderLineProductParts().stream()
                            .mapToDouble(
                                    orderlineproductpart -> orderlineproductpart.finalPrice() * orderLine.quantity())
                            .sum())
                    .sum();
            if (Math.abs(finalPriceToCheck - sumFinalPrice) > epsilon) {
                throw new IllegalArgumentException("Final price doesn't match with the sum of the base prices");
            }
            Order order = OrderMapper.toModel(orderDTO);
            Long orderId = orderDAO.insert(connection, order);
            List<Long> insertedOrderLines = orderLineService.insertAll(connection, orderDTO.orderLines(), orderId);
            if (insertedOrderLines.isEmpty()) {
                throw new IllegalArgumentException("Failed to insert order lines");
            }
            return orderId;

        });

    }

    // The method is not currently utilized, but it may become useful for future
    // development or feature enhancements.
    public Boolean update(Order order) {
        return TransactionHandler.startTransaction((connection) -> {
            return orderDAO.update(connection, order);
        });

    }

    public Boolean delete(Long id) {
        return TransactionHandler.startTransaction((connection) -> {
            return orderDAO.delete(connection, id);
        });
    }

    public List<OrderDTO> getByUserId(Long userId) {
        return TransactionHandler.startTransaction((connection) -> {
            Map<Long, List<OrderLineDTO>> orderLinesMap = orderLineService.getAllGroupedByOrder();
            return orderDAO.getAllByUser(connection, userId).stream()
                    .map(order -> OrderMapper.toDTO(order,
                            orderLinesMap.getOrDefault(order.getId(), new ArrayList<>())))
                    .collect(Collectors.toList());
        });
    }

}
