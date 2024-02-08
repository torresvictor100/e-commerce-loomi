package com.loomi.ecommerce.service;

import com.loomi.ecommerce.entity.Order;
import com.loomi.ecommerce.entity.OrderStatus;
import com.loomi.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> findAll(){
        return orderRepository.findAll();
    }

    public Order save(Order order){
        order.setId(null);
        return orderRepository.save(order);
    }

    public Order findById(Long id) {
        Optional<Order> optionalOrder=  orderRepository.findById(id);
        return optionalOrder.orElse(null);
    }

    public List<Order> findByOrderDate(Date startDate, Date endDate) {
        List<Order> listOrder=  orderRepository.findByOrderDateBetween(startDate, endDate);
        return listOrder;
    }

    public Order update(Order order) {
        Order  orderFound = findById(order.getId());
        if (orderFound != null) {
            return orderRepository.save(order);
        }else{
            return order;
        }
    }

    public void deleteById(Long id) {
        Order order = new Order();
        order.setId(id);
        orderRepository.delete(order);

    }


    public Order updateStatusOrdem(Order order) {
        Order orderFound = findById(order.getId());
        if (orderFound != null) {
            OrderStatus currentStatus = orderFound.getStatus();
            OrderStatus nextStatus = getNextStatus(currentStatus);
            orderFound.setStatus(nextStatus);
            return orderRepository.save(orderFound);
        } else {
            return order;
        }
    }

    private OrderStatus getNextStatus(OrderStatus currentStatus) {
        switch (currentStatus) {
            case RECEIVED:
                return OrderStatus.INPREPARATION;
            case INPREPARATION:
                return OrderStatus.DISPATCHED;
            case DISPATCHED:
                return OrderStatus.DELIVERED;
            case DELIVERED:
                return OrderStatus.DELIVERED;
            default:
                throw new IllegalArgumentException("Unexpected current status: " + currentStatus);
        }
    }
}
