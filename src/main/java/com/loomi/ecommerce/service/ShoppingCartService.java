package com.loomi.ecommerce.service;

import com.loomi.ecommerce.entity.Order;
import com.loomi.ecommerce.entity.OrderItem;
import com.loomi.ecommerce.entity.OrderStatus;
import com.loomi.ecommerce.entity.ShoppingCart;
import com.loomi.ecommerce.exeception.InsufficientStockException;
import com.loomi.ecommerce.exeception.ProductNotFoundException;
import com.loomi.ecommerce.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {
    @Autowired
    final private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    final private OrderItemShoppingCartService orderItemShoppingCartService;
    @Autowired
    final private OrderService orderService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository,
                               OrderItemShoppingCartService orderItemShoppingCartService,
                               OrderService orderService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.orderItemShoppingCartService = orderItemShoppingCartService;
        this.orderService = orderService;
    }

    public List<ShoppingCart> findAll() {
        return shoppingCartRepository.findAll();
    }

    public ShoppingCart save(ShoppingCart shoppingCart) {
        shoppingCart.setId(null);
        return shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart findById(Long id) {
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findById(id);
        return optionalShoppingCart.orElse(null);
    }

    public ShoppingCart update(ShoppingCart shoppingCart) {
        ShoppingCart shoppingCartFound = findById(shoppingCart.getId());
        if (shoppingCartFound != null) {
            return shoppingCartRepository.save(shoppingCart);
        } else {
            return shoppingCart;
        }
    }

    public void deleteById(Long id) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(id);
        shoppingCartRepository.delete(shoppingCart);

    }

    public Order convertShoppingCarInOrder(Long id)
            throws InsufficientStockException, ProductNotFoundException {
        ShoppingCart optionalShoppingCart = this.findById(id);
        return convertShoppingCarInOrder(optionalShoppingCart);
    }

    private Order convertShoppingCarInOrder(ShoppingCart shoppingCart)
            throws InsufficientStockException, ProductNotFoundException {

        Order order = createOrder(shoppingCart);

        List<OrderItem> listOrderItem = orderItemShoppingCartService
                .convertOrderItemShoppingCartToOrderItem(shoppingCart.getOrderItemsShoppingCart(), order);
        order.setOrderItems(listOrderItem);

        return orderService.update(order);
    }

    private Order createOrder(ShoppingCart shoppingCart) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Order order = new Order();
        order.setId(null);
        order.setClientId(shoppingCart.getClientId());
        order.setOrderDate(timestamp);
        order.setStatus(OrderStatus.RECEIVED);
        return orderService.save(order);
    }


}
