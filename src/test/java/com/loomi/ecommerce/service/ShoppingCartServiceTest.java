package com.loomi.ecommerce.service;


import com.loomi.ecommerce.entity.*;
import com.loomi.ecommerce.exeception.*;
import com.loomi.ecommerce.repository.OrderRepository;
import com.loomi.ecommerce.repository.ShoppingCartRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ShoppingCartServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderItemShoppingCartService orderItemShoppingCartService;

    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testConvertShoppingCartInOrder() throws InsufficientStockException, ProductNotFoundException {

        User user = new User(1L, "test", "test@gmail.com", "123", timestamp, timestamp
                , UserType.ADMIN, null);

        user = userService.save(user);

        Client client = new Client(1L, user.getId(), null, "test", "0123456789", "street zero",
                true, null, null, timestamp, timestamp);

        client = clientService.save(client);

        Product product = new Product(1L, "test", "test", 10.0, 10,
                timestamp, timestamp, "test", null, null);

        product = productService.save(product);


        ShoppingCart shoppingCart = new ShoppingCart(1L, client.getId(), null, null);

        shoppingCart = shoppingCartService.save(shoppingCart);

        OrderItemShoppingCart orderItemShoppingCart = new OrderItemShoppingCart(1L, shoppingCart.getId()
                , null, product.getId(), null, 5
                , BigDecimal.TEN, BigDecimal.valueOf(50));

        orderItemShoppingCart = orderItemShoppingCartService.save(orderItemShoppingCart);

        List<OrderItemShoppingCart> listOrderItemShoppingCart = new ArrayList<>();
        listOrderItemShoppingCart.add(orderItemShoppingCart);

        shoppingCart.setOrderItemsShoppingCart(listOrderItemShoppingCart);

        shoppingCart = shoppingCartService.update(shoppingCart);

        Order order = new Order(1L, client.getId(), null, null, OrderStatus.RECEIVED, timestamp,
                BigDecimal.ZERO);

        order = orderService.save(order);

        OrderItem orderItem = new OrderItem(1L, order.getId(), null, product.getId(), null, 5
                , BigDecimal.TEN, BigDecimal.valueOf(50));

        orderItemService.save(orderItem);

        List<OrderItem> listOrdemIntem = new ArrayList<>();
        listOrdemIntem.add(orderItem);


        order.setOrderItems(listOrdemIntem);

        orderService.update(order);


        Order order2 = shoppingCartService.convertShoppingCarInOrder(shoppingCart.getId());

        assertNotNull(order2);

        Product product1 = productService.findById(order2.getOrderItems().get(0).getProductId());

        assertEquals(5, product1.getQuantityInStock());

        Client client1 = clientService.findById(order2.getClientId());

        assertEquals("test", client1.getFullName());

        assertEquals(BigDecimal.valueOf(50), order2.getTotalAmount());

    }

}