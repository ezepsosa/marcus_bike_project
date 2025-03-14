package com.ezepsosa.marcusbike.services;

import java.util.List;
import java.util.stream.Collectors;

import com.ezepsosa.marcusbike.dto.ProductDTO;
import com.ezepsosa.marcusbike.dto.ProductInsertDTO;
import com.ezepsosa.marcusbike.mappers.ProductMapper;
import com.ezepsosa.marcusbike.repositories.ProductDAO;
import com.ezepsosa.marcusbike.utils.TransactionHandler;

public class ProductService {

    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<ProductDTO> getAll() {
        return TransactionHandler.startTransaction((connection) -> {
            return productDAO.getAll(connection).stream().map(product -> ProductMapper.toDTO(product))
                    .collect(Collectors.toList());
        });
    }

    public ProductDTO getById(Long id) {
        return TransactionHandler.startTransaction((connection) -> {
            return ProductMapper.toDTO(productDAO.getById(connection, id));
        });
    }

    public Long insert(ProductInsertDTO product) {
        return TransactionHandler.startTransaction((connection) -> {
            return productDAO.insert(connection, ProductMapper.toModel(product));
        });
    }

    public boolean update(ProductInsertDTO product, Long id) {
        return TransactionHandler.startTransaction((connection) -> {
            return productDAO.update(connection, ProductMapper.toModel(product), id);
        });
    }

    public boolean delete(Long id) {
        return TransactionHandler.startTransaction((connection) -> {
            return productDAO.delete(connection, id);
        });
    }

}
