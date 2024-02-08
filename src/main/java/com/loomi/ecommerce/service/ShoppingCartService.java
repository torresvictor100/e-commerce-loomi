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
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private OrderItemShoppingCartService orderItemShoppingCartService;

    @Autowired
    private OrderService orderService;

    public List<ShoppingCart> findAll(){
        return shoppingCartRepository.findAll();
    }

    public ShoppingCart save(ShoppingCart shoppingCart){
        shoppingCart.setId(null);
        return shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart findById(Long id) {
        Optional<ShoppingCart> optionalShoppingCart =  shoppingCartRepository.findById(id);
        return optionalShoppingCart.orElse(null);
    }

    public ShoppingCart update(ShoppingCart shoppingCart) {
        ShoppingCart shoppingCartFound = findById(shoppingCart.getId());
        if (shoppingCartFound != null) {
            return shoppingCartRepository.save(shoppingCart);
        }else{
            return shoppingCart;
        }
    }

    public void deleteById(Long id) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(id);
        shoppingCartRepository.delete(shoppingCart);

    }

    public Order convertShoppingCarInOrder(Long id) throws InsufficientStockException, ProductNotFoundException {
        Optional<ShoppingCart> optionalShoppingCart =  shoppingCartRepository.findById(id);
        return convertShoppingCarInOrder(optionalShoppingCart.get());
    }

    private Order convertShoppingCarInOrder(ShoppingCart shoppingCart) throws InsufficientStockException, ProductNotFoundException {
        Order order = new Order();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        order.setId(null);
        order.setClientId(shoppingCart.getClientId());
        order.setOrderDate(timestamp);
        order.setStatus(OrderStatus.RECEIVED);
        order.setTotalAmount(BigDecimal.ZERO);
        Order orderSave = orderService.save(order);
        List<OrderItem> listOrderItem = orderItemShoppingCartService.ConvertOrderItemShoppingCarttoOrderItem(shoppingCart.getOrderItemsShoppingCart(), orderSave);
        orderSave.setOrderItems(listOrderItem);
        return orderService.update(orderSave);

    }
}
