package com.marcusbike.marcus_bike_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcusbike.marcus_bike_api.models.ProductPart;

public interface ProductPartRepository extends JpaRepository<ProductPart, Long>{

}
