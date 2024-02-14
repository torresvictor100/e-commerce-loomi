package com.loomi.ecommerce.service;

import com.loomi.ecommerce.entity.*;
import com.loomi.ecommerce.exeception.InsufficientStockException;
import com.loomi.ecommerce.exeception.ProductNotFoundException;
import com.loomi.ecommerce.repository.OrderItemShoppingCartRepository;
import com.loomi.ecommerce.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderItemShoppingCartService {
    @Autowired
    final private OrderItemShoppingCartRepository orderItemShoppingCartRepository;
    @Autowired
    final private OrderItemService orderItemService;
    @Autowired
    final private ProductService productService;
    @Autowired
    final private OrderService orderService;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;


    public OrderItemShoppingCartService(OrderItemShoppingCartRepository orderItemShoppingCartRepository,
                                        OrderItemService orderItemService, ProductService productService,
                                        OrderService orderService) {
        this.orderItemShoppingCartRepository = orderItemShoppingCartRepository;
        this.orderItemService = orderItemService;
        this.productService = productService;
        this.orderService = orderService;

    }

    public List<OrderItemShoppingCart> findAll() {
        return orderItemShoppingCartRepository.findAll();
    }


    public OrderItemShoppingCart save(OrderItemShoppingCart orderItemShoppingCart) {
        orderItemShoppingCart.setId(null);
        return orderItemShoppingCartRepository.save(orderItemShoppingCart);
    }

    public OrderItemShoppingCart save(OrderItemShoppingCart orderItemShoppingCart, User authenticatedUser) {
        orderItemShoppingCart.setId(null);
        if (authenticatedUser.isAdmin()) {
            return orderItemShoppingCartRepository.save(orderItemShoppingCart);
        } else if (certificationUser(orderItemShoppingCart, authenticatedUser)) {
            return orderItemShoppingCartRepository.save(orderItemShoppingCart);
        }
        return null;
    }

    public OrderItemShoppingCart findById(Long id) {
        Optional<OrderItemShoppingCart> optionalOrderItemShoppingCart = orderItemShoppingCartRepository.findById(id);
        return optionalOrderItemShoppingCart.orElse(null);
    }

    public OrderItemShoppingCart findById(Long id, User authenticatedUser) {
        Optional<OrderItemShoppingCart> optionalOrderItemShoppingCart = orderItemShoppingCartRepository.findById(id);
        if (optionalOrderItemShoppingCart.isPresent()) {
            return getOrderItemShoppingCart(authenticatedUser, optionalOrderItemShoppingCart);
        }
        return null;
    }

    private OrderItemShoppingCart getOrderItemShoppingCart(User authenticatedUser, Optional<OrderItemShoppingCart> optionalOrderItemShoppingCart) {
        if (authenticatedUser.isAdmin()) {
            return optionalOrderItemShoppingCart.get();
        } else if (certificationUser(optionalOrderItemShoppingCart.get(), authenticatedUser)) {
            return optionalOrderItemShoppingCart.get();
        }
        return null;
    }

    public OrderItemShoppingCart update(OrderItemShoppingCart orderItemShoppingCart) {
        OrderItemShoppingCart orderItemShoppingCartound = findById(orderItemShoppingCart.getId());
        if (orderItemShoppingCartound != null) {
            return orderItemShoppingCartRepository.save(orderItemShoppingCart);
        } else {
            return orderItemShoppingCart;
        }
    }

    public void deleteById(Long id) {
        OrderItemShoppingCart orderItemShoppingCart = new OrderItemShoppingCart();
        orderItemShoppingCart.setId(id);
        orderItemShoppingCartRepository.delete(orderItemShoppingCart);

    }

    public List<OrderItem> convertOrderItemShoppingCartToOrderItem
            (List<OrderItemShoppingCart> listOrderItemShoppingCart, Order order)
            throws InsufficientStockException, ProductNotFoundException {
        List<OrderItem> listOrderItems = new ArrayList<>();

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemShoppingCart itemShoppingCart : listOrderItemShoppingCart) {
            OrderItem orderItem = createOrderItem(order, itemShoppingCart);
            productService.removeProductByQuantity(orderItem.getQuantity(), productService.findById(orderItem.getProductId()));
            listOrderItems.add(orderItem);
            totalAmount = totalAmount.add(itemShoppingCart.getSubtotal());
        }
        order.setTotalAmount(totalAmount);
        orderService.update(order);
        return listOrderItems;
    }

    private OrderItem createOrderItem(Order order, OrderItemShoppingCart itemShoppingCart) {
        OrderItem orderItem = new OrderItem();

        orderItem.setId(null);
        orderItem.setOrderId(order.getId());
        orderItem.setOrder(order);
        orderItem.setQuantity(itemShoppingCart.getQuantity());
        orderItem.setUnitPrice(itemShoppingCart.getUnitPrice());
        orderItem.setProductId(itemShoppingCart.getProductId());
        orderItem.setProduct(itemShoppingCart.getProduct());

        return orderItemService.save(orderItem);
    }

    private boolean certificationUser(OrderItemShoppingCart orderItemShoppingCart, User authenticatedUser) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(orderItemShoppingCart.getShoppingCartId()).get();
        User userShoppingCart = shoppingCart.getClient().getUser();
        if (userShoppingCart.getId() == authenticatedUser.getId()) {
            return true;
        } else {
            return false;
        }
    }
}
