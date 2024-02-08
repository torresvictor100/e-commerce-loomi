package com.loomi.ecommerce.service;

import com.loomi.ecommerce.entity.Order;
import com.loomi.ecommerce.entity.OrderItem;
import com.loomi.ecommerce.entity.OrderItemShoppingCart;
import com.loomi.ecommerce.entity.Product;
import com.loomi.ecommerce.exeception.InsufficientStockException;
import com.loomi.ecommerce.exeception.ProductNotFoundException;
import com.loomi.ecommerce.repository.OrderItemShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderItemShoppingCartService {

    @Autowired
    private OrderItemShoppingCartRepository orderItemShoppingCartRepository;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ProductService productService;

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

    public List<OrderItem> ConvertOrderItemShoppingCarttoOrderItem
            (List<OrderItemShoppingCart>  listOrderItemShoppingCart, Order order) throws InsufficientStockException, ProductNotFoundException {
        List<OrderItem> listOrderItems = new ArrayList<>();

        for (OrderItemShoppingCart itemShoppingCart : listOrderItemShoppingCart) {
            OrderItem orderItem = new OrderItem();

            orderItem.setId(null);
            orderItem.setOrderId(order.getId());
            orderItem.setOrder(order);
            orderItem.setQuantity(itemShoppingCart.getQuantity());
            orderItem.setUnitPrice(itemShoppingCart.getUnitPrice());
            orderItem.setProductId(itemShoppingCart.getProductId());
            orderItem.setProduct(itemShoppingCart.getProduct());

            orderItemService.save(orderItem);
            productService.removeProductByQuantity(orderItem.getQuantity(), orderItem.getProduct());
            listOrderItems.add(orderItem);
        }
        return listOrderItems;
    }
}
