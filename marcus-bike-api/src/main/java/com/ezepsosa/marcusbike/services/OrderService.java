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

public class OrderService {

    private final OrderDAO orderDAO;
    private final OrderLineService orderLineService;

    public OrderService(OrderDAO orderDAO, OrderLineService orderLineService) {
        this.orderDAO = orderDAO;
        this.orderLineService = orderLineService;
    }

    public List<OrderDTO> getAll() {
        Map<Long, List<OrderLineDTO>> orderLinesMap = orderLineService.getAllGroupedByOrder();
        return orderDAO.getAll().stream()
                .map(order -> OrderMapper.toDTO(order,
                        orderLinesMap.getOrDefault(order.getId(), orderLinesMap.get(order.getId()))))
                .collect(Collectors.toList());

    }

    public OrderDTO getById(Long id) {
        List<OrderLineDTO> orderLines = orderLineService.getByOrderId(id);
        Order order = orderDAO.getById(id);
        if (order != null) {
            return OrderMapper.toDTO(order, orderLines);
        } else {
            return null;
        }
    }

    public Long insert(OrderInsertDTO orderDTO) {
        return TransactionHandler.startTransaction(() -> {
            Double finalPriceToCheck = orderDTO.finalPrice();
            Double sumFinalPrice = orderDTO.orderLines().stream()
                    .mapToDouble(orderLine -> orderLine.orderLineProductParts().stream()
                            .mapToDouble(
                                    orderlineproductpart -> orderlineproductpart.finalPrice() * orderLine.quantity())
                            .sum())
                    .sum();
            if (finalPriceToCheck.equals(sumFinalPrice)) {
                Order order = OrderMapper.toModel(orderDTO);
                Long orderId = orderDAO.insert(order);
                Boolean res = orderLineService.insertAll(orderDTO.orderLines(),
                        orderId);
                return orderId;
            } else {
                return Long.valueOf(1);
            }
        });

    }

    public Boolean update(Order order) {
        return orderDAO.update(order);
    }

    public Boolean delete(Long id) {
        return orderDAO.delete(id);
    }

    public List<OrderDTO> getByUserId(Long userId) {
        Map<Long, List<OrderLineDTO>> orderLinesMap = orderLineService.getAllGroupedByOrder();
        return orderDAO.getAllByUser(userId).stream()
                .map(order -> OrderMapper.toDTO(order, orderLinesMap.getOrDefault(order.getId(), new ArrayList<>())))
                .collect(Collectors.toList());
    }

}
