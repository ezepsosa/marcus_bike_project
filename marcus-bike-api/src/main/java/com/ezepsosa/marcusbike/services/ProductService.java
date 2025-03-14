package com.ezepsosa.marcusbike.services;

import java.util.List;
import java.util.stream.Collectors;

import com.ezepsosa.marcusbike.dto.ProductDTO;
import com.ezepsosa.marcusbike.dto.ProductInsertDTO;
import com.ezepsosa.marcusbike.mappers.ProductMapper;
import com.ezepsosa.marcusbike.repositories.ProductDAO;

public class ProductService {

    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<ProductDTO> getAll() {
        return productDAO.getAll().stream().map(product -> ProductMapper.toDTO(product))
                .collect(Collectors.toList());
    }

    public ProductDTO getById(Long id) {
        return ProductMapper.toDTO(productDAO.getById(id));
    }

    public Long insert(ProductInsertDTO product) {
        return productDAO.insert(ProductMapper.toModel(product));
    }

    public boolean update(ProductInsertDTO product, Long id) {
        return productDAO.update(ProductMapper.toModel(product), id);
    }

    public boolean delete(Long id) {
        return productDAO.delete(id);
    }

}
