package com.loomi.ecommerce.service;

import com.loomi.ecommerce.entity.*;
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
    @Autowired
    private ClientService clientService;

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

    public ShoppingCart save(ShoppingCart shoppingCart, User authenticatedUser) {
        shoppingCart.setId(null);
        if (authenticatedUser.isAdmin()) {
            return shoppingCartRepository.save(shoppingCart);
        } else if (certificationUser(shoppingCart, authenticatedUser)) {
            return shoppingCartRepository.save(shoppingCart);
        }
        return null;
    }

    public ShoppingCart findById(Long id) {
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findById(id);
        return optionalShoppingCart.orElse(null);
    }

    public ShoppingCart findById(Long id, User authenticatedUser) {
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findById(id);
        if (optionalShoppingCart.isPresent()) {
            return getShoppingCart(authenticatedUser, optionalShoppingCart);
        }
        return null;
    }

    private ShoppingCart getShoppingCart(User authenticatedUser, Optional<ShoppingCart> optionalShoppingCart) {
        if (authenticatedUser.isAdmin()) {
            return optionalShoppingCart.get();
        } else if (certificationUser(optionalShoppingCart.get(), authenticatedUser)) {
            return optionalShoppingCart.get();
        }
        return null;
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

    private boolean certificationUser(ShoppingCart shoppingCart, User authenticatedUser) {
        User userShoppingCart = clientService.findById(shoppingCart.getClientId()).getUser();
        if (userShoppingCart.getId() == authenticatedUser.getId()) {
            return true;
        } else {
            return false;
        }
    }
}
