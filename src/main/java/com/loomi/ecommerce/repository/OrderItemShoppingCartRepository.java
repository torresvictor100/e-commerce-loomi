package com.loomi.ecommerce.repository;

import com.loomi.ecommerce.entity.OrderItemShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemShoppingCartRepository extends JpaRepository<OrderItemShoppingCart, Long> {
}
