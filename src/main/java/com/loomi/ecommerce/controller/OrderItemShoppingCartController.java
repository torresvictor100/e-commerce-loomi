package com.loomi.ecommerce.controller;

import com.loomi.ecommerce.entity.OrderItemShoppingCart;
import com.loomi.ecommerce.service.OrderItemShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderitemshoppingcart")
public class OrderItemShoppingCartController {

    @Autowired
    private OrderItemShoppingCartService orderItemShoppingCartService;

    @Operation(summary = "Find all Order Item Shopping Cart", tags = "OrderItemShoppingCart")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OrderItemShoppingCart>> findAll(){
        return ResponseEntity.ok(orderItemShoppingCartService.findAll());
    }

    @Operation(summary = "Find Order Item Shopping Cart by ID", tags = "OrderItemShoppingCart")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found") })
    @GetMapping(path = "/{order_item_shopping_cart_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OrderItemShoppingCart> findById(@PathVariable(name = "order_item_shopping_cart_id") Long id) {
        OrderItemShoppingCart orderItemShoppingCart = orderItemShoppingCartService.findById(id);
        if (orderItemShoppingCart == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(orderItemShoppingCart, HttpStatus.OK);
        }
    }

    @Operation(summary = "Save a new Order Item Shopping Cart", tags = "OrderItemShoppingCart")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrderItemShoppingCart> save(@RequestBody OrderItemShoppingCart orderItemShoppingCart) {
        try {
            orderItemShoppingCart = orderItemShoppingCartService.save(orderItemShoppingCart);
            return new ResponseEntity<>(orderItemShoppingCart, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Update a Order Item Shopping Cart", tags = "OrderItemShoppingCart")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found") })
    @PutMapping(path = "/{order_item_shopping_cart_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OrderItemShoppingCart> update(@PathVariable(name = "order_item_shopping_cart_id") Long id,
                                               @RequestBody OrderItemShoppingCart orderItemShoppingCart) {
        orderItemShoppingCart.setId(id);
        try {
            orderItemShoppingCart = orderItemShoppingCartService.update(orderItemShoppingCart);
            if (orderItemShoppingCart == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(orderItemShoppingCart, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete a Order Item Shopping Cart", tags = "OrderItemShoppingCart")
    @ApiResponses({ @ApiResponse(responseCode = "204", description = "No Content") })
    @DeleteMapping(path = "/{order_item_shopping_cart_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteById(@PathVariable(name = "order_item_shopping_cart_id") Long id) {
        orderItemShoppingCartService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
