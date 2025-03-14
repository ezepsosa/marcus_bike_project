package com.ezepsosa.marcusbike.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ezepsosa.marcusbike.dto.ProductPartDTO;
import com.ezepsosa.marcusbike.mappers.ProductPartMapper;
import com.ezepsosa.marcusbike.repositories.ProductPartDAO;
import com.ezepsosa.marcusbike.utils.TransactionHandler;

public class ProductPartService {

    private final ProductPartDAO productPartDAO;

    public ProductPartService(ProductPartDAO productPartDAO) {
        this.productPartDAO = productPartDAO;
    }

    public List<ProductPartDTO> getAll() {
        return TransactionHandler.startTransaction((connection) -> {
            return productPartDAO.getAll(connection).stream().map(productPart -> ProductPartMapper.toDTO(productPart))
                    .collect(Collectors.toList());
        });

    }

    public List<ProductPartDTO> getByProductId(Long productId) {
        return TransactionHandler.startTransaction((connection) -> {
            return productPartDAO.getAllByProductId(connection, productId).stream()
                    .map(productPart -> ProductPartMapper.toDTO(productPart))
                    .collect(Collectors.toList());
        });
    }

    Map<Long, Double> getAllPartPriceById(List<Long> productIds) {
        return TransactionHandler.startTransaction((connection) -> {
            return productPartDAO.getAllByProductPartId(connection, productIds).stream()
                    .map(productPart -> ProductPartMapper.toDTO(productPart))
                    .collect(Collectors.toMap(ProductPartDTO::id, ProductPartDTO::basePrice));
        });
    }

    ///////////////////
    public ProductPartDTO getById(Long id) {
        return TransactionHandler.startTransaction((connection) -> {
            return ProductPartMapper.toDTO(productPartDAO.getById(connection, id));
        });
    }

    public Map<Long, Double> getAllPartPrice() {
        return TransactionHandler.startTransaction((connection) -> {
            return productPartDAO.getAll(connection).stream().map(productPart -> ProductPartMapper.toDTO(productPart))
                    .collect(Collectors.toMap(ProductPartDTO::id, ProductPartDTO::basePrice));
        });

    }

}
