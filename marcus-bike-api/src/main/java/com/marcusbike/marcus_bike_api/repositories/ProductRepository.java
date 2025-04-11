package com.marcusbike.marcus_bike_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marcusbike.marcus_bike_api.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
