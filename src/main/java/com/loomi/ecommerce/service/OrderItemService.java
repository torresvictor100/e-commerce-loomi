package com.loomi.ecommerce.service;

import com.loomi.ecommerce.entity.OrderItem;
import com.loomi.ecommerce.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {
    @Autowired
    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    public OrderItem save(OrderItem orderItem) {
        orderItem.setId(null);
        return orderItemRepository.save(orderItem);
    }

    public OrderItem findById(Long id) {
        Optional<OrderItem> orderItem = orderItemRepository.findById(id);
        return orderItem.orElse(null);
    }

    public OrderItem update(OrderItem orderItem) {
        OrderItem orderItemFound = findById(orderItem.getId());
        if (orderItemFound != null) {
            return orderItemRepository.save(orderItemFound);
        } else {
            return orderItem;
        }
    }

    public void deleteById(Long id) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(id);
        orderItemRepository.delete(orderItem);
    }
}
