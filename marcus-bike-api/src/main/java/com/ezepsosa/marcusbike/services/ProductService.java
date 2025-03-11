package com.ezepsosa.marcusbike.services;

import java.util.List;

import com.ezepsosa.marcusbike.models.Product;
import com.ezepsosa.marcusbike.repositories.ProductDAO;

public class ProductService {

    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<Product> getAll() {
        return productDAO.getAll();
    }

    public Product getById(Long id) {
        return productDAO.getById(id);
    }

    public Long insert(Product user) {
        return productDAO.insert(user);
    }

    public boolean update(Product user) {
        return productDAO.update(user);
    }

    public boolean delete(Long id) {
        return productDAO.delete(id);
    }

}
