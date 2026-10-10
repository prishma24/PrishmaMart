package com.prishmamart.prishma_mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.prishmamart.prishma_mart.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}