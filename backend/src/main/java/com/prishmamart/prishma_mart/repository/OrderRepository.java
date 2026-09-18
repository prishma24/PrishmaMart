package com.prishmamart.prishma_mart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.prishmamart.prishma_mart.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}