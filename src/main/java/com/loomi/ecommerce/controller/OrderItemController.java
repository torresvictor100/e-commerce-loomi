package com.loomi.ecommerce.controller;

import com.loomi.ecommerce.entity.OrderItem;
import com.loomi.ecommerce.service.OrderItemService;
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
@RequestMapping("/orderitem")
@SecurityRequirement(name = "Bearer Authentication")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @Operation(summary = "Find all OrderItem", tags = "OrderItem")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OrderItem>> findAll() {
        return ResponseEntity.ok(orderItemService.findAll());
    }

    @Operation(summary = "Find OrderItem by ID", tags = "OrderItem")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @GetMapping(path = "/{order_item_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OrderItem> findById(@PathVariable(name = "order_item_id") Long id) {
        OrderItem orderItem = orderItemService.findById(id);
        if (orderItem == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(orderItem, HttpStatus.OK);
        }
    }

    @Operation(summary = "Save a new OrderItem", tags = "OrderItem")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrderItem> save(@RequestBody OrderItem orderItem) {
        try {
            orderItem = orderItemService.save(orderItem);
            return new ResponseEntity<>(orderItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Update a OrderItem", tags = "OrderItem")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @PutMapping(path = "/{order_item_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OrderItem> update(@PathVariable(name = "order_item_id") Long id,
                                            @RequestBody OrderItem orderItem) {
        orderItem.setId(id);
        try {
            orderItem = orderItemService.update(orderItem);
            if (orderItem == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(orderItem, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete a OrderItem", tags = "OrderItem")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "No Content")})
    @DeleteMapping(path = "/{order_item_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteById(@PathVariable(name = "order_item_id") Long id) {
        orderItemService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
