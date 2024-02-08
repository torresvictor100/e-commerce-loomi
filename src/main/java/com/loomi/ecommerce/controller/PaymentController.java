package com.loomi.ecommerce.controller;

import com.loomi.ecommerce.entity.Order;
import com.loomi.ecommerce.exeception.InsufficientStockException;
import com.loomi.ecommerce.exeception.ProductNotFoundException;
import com.loomi.ecommerce.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Operation(summary = "Payment Confirmed", tags = "Payment")
    @PostMapping(path = "/{shopping_card_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Order paymentConfirmed(@RequestBody Boolean statusPayment,@PathVariable(name = "shopping_card_id") Long shoppingCardId){
        if(statusPayment != null && statusPayment){
            try {
                return paymentService.paymentConfirmed(shoppingCardId);
            } catch (InsufficientStockException e) {
                throw new RuntimeException(e);
            } catch (ProductNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order awaiting payment.");
        }
    }
}
