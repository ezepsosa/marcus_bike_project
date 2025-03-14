package com.ezepsosa.marcusbike.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ezepsosa.marcusbike.dto.OrderLineDTO;
import com.ezepsosa.marcusbike.dto.OrderLineInsertDTO;
import com.ezepsosa.marcusbike.dto.OrderLineProductPartInsertDTO;
import com.ezepsosa.marcusbike.mappers.OrderLineMapper;
import com.ezepsosa.marcusbike.models.OrderLine;
import com.ezepsosa.marcusbike.repositories.OrderLineDAO;

public class OrderLineService {

    private final OrderLineDAO orderLineDAO;
    private final OrderLineProductPartService orderLineProductPartService;

    public OrderLineService(OrderLineDAO orderLineDAO, OrderLineProductPartService orderLineProductPartService) {
        this.orderLineDAO = orderLineDAO;
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
                        .map(OrderLineProductPartInsertDTO::productPart))
                .collect(Collectors.toList());
        List<OrderLine> orderLines = orderLineInsertDTO.stream()
                .map(orderlinedto -> OrderLineMapper.toModel(orderlinedto)).collect(Collectors.toList());
        List<Long> ol = orderLineDAO.insertAll(orderLines, orderId);
        orderLineProductPartService.insertAll(orderLineInsertDTO.get(0).orderLineProductParts(), orderId, productIds);

        throw new UnsupportedOperationException("Unimplemented method 'insertAllLines'");
    }

}
