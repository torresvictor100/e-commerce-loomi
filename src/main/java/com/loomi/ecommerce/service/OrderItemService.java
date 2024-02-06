package com.loomi.ecommerce.service;

import com.loomi.ecommerce.repository.ClientRepository;
import com.loomi.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {

    @Autowired
    private OrderRepository orderRepository;

    public OrderItemService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
