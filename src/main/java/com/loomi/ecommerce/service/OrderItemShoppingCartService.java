package com.loomi.ecommerce.service;

import com.loomi.ecommerce.entity.OrderItemShoppingCart;
import com.loomi.ecommerce.repository.OrderItemShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemShoppingCartService {

    @Autowired
    private OrderItemShoppingCartRepository orderItemShoppingCartRepository;

    public List<OrderItemShoppingCart> findAll(){
        return orderItemShoppingCartRepository.findAll();
    }

    public OrderItemShoppingCart save(OrderItemShoppingCart orderItemShoppingCart){
        orderItemShoppingCart.setId(null);
        return orderItemShoppingCartRepository.save(orderItemShoppingCart);
    }

    public OrderItemShoppingCart findById(Long id) {
        Optional<OrderItemShoppingCart> optionalOrderItemShoppingCart =  orderItemShoppingCartRepository.findById(id);
        return optionalOrderItemShoppingCart.orElse(null);
    }

    public OrderItemShoppingCart update(OrderItemShoppingCart orderItemShoppingCart) {
        OrderItemShoppingCart orderItemShoppingCartound = findById(orderItemShoppingCart.getId());
        if (orderItemShoppingCartound != null) {
            return orderItemShoppingCartRepository.save(orderItemShoppingCart);
        }else{
            return orderItemShoppingCart;
        }
    }

    public void deleteById(Long id) {
        OrderItemShoppingCart orderItemShoppingCart = new OrderItemShoppingCart();
        orderItemShoppingCart.setId(id);
        orderItemShoppingCartRepository.delete(orderItemShoppingCart);

    }
}
