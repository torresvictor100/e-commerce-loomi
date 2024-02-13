package com.loomi.ecommerce.controller;

import com.loomi.ecommerce.entity.Order;
import com.loomi.ecommerce.entity.ShoppingCart;
import com.loomi.ecommerce.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppingcart")
@SecurityRequirement(name = "Bearer Authentication")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;


    @Operation(summary = "Find all Shopping Cart", tags = "ShoppingCart")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ShoppingCart>> findAll() {
        return ResponseEntity.ok(shoppingCartService.findAll());
    }

    @Operation(summary = "Find Shopping Cart by ID", tags = "ShoppingCart")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @GetMapping(path = "/{shopping_cart_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ShoppingCart> findById(@PathVariable(name = "shopping_cart_id") Long id) {
        ShoppingCart shoppingCart = shoppingCartService.findById(id);
        if (shoppingCart == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(shoppingCart, HttpStatus.OK);
        }
    }

    @Operation(summary = "Save a new Shopping Cart", tags = "ShoppingCart")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ShoppingCart> save(@RequestBody ShoppingCart shoppingCart) {
        try {
            shoppingCart = shoppingCartService.save(shoppingCart);
            return new ResponseEntity<>(shoppingCart, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "convert Shopping Car In Order", tags = "ShoppingCart")
    @PostMapping(path = "/convert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Order> convertShoppingCarInOrder(@RequestBody ShoppingCart shoppingCart) {
        try {
            Order order = shoppingCartService.convertShoppingCarInOrder(shoppingCart.getId());
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Update a Shopping Cart", tags = "ShoppingCart")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @PutMapping(path = "/{shopping_cart_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ShoppingCart> update(@PathVariable(name = "shopping_cart_id") Long id,
                                               @RequestBody ShoppingCart shoppingCart) {
        shoppingCart.setId(id);
        try {
            shoppingCart = shoppingCartService.update(shoppingCart);
            if (shoppingCart == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(shoppingCart, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete a Shopping Cart", tags = "ShoppingCart")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "No Content")})
    @DeleteMapping(path = "/{shopping_cart_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteById(@PathVariable(name = "shopping_cart_id") Long id) {
        shoppingCartService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
