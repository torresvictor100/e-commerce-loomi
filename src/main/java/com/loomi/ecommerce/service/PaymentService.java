package com.loomi.ecommerce.service;

import com.loomi.ecommerce.entity.Order;
import com.loomi.ecommerce.exeception.InsufficientStockException;
import com.loomi.ecommerce.exeception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    final private ShoppingCartService shoppingCartService;
    @Autowired
    final private EmailService emailService;
    @Value("${email.subject}")
    private String emailSubject;
    @Value("${email.body}")
    private String emailBody;

    public PaymentService(ShoppingCartService shoppingCartService, EmailService emailService) {
        this.shoppingCartService = shoppingCartService;
        this.emailService = emailService;
    }

    public Order paymentConfirmed(Long shoppingId) throws InsufficientStockException, ProductNotFoundException {
        Order order = shoppingCartService.convertShoppingCarInOrder(shoppingId);
        String destinatario = "loomiecommerce@gmail.com";
        emailService.sendEmail(destinatario, emailSubject, emailBody);
        return order;

    }
}
