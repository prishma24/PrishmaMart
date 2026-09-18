package com.prishmamart.prishma_mart;

import org.springframework.web.bind.annotation.*;
import com.prishmamart.prishma_mart.model.Order;
import com.prishmamart.prishma_mart.repository.OrderRepository;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public Order placeOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }
}