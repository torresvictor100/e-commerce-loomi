package com.loomi.ecommerce.service;

import com.loomi.ecommerce.entity.Order;
import com.loomi.ecommerce.exeception.InsufficientStockException;
import com.loomi.ecommerce.exeception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PaymentService {
    @Autowired
    private ShoppingCartService shoppingCartService;

    public Order paymentConfirmed(Long shoppingId) throws InsufficientStockException, ProductNotFoundException {
        return shoppingCartService.convertShoppingCarInOrder(shoppingId);

    }
}
