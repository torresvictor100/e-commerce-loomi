package com.loomi.ecommerce.controller;

import com.loomi.ecommerce.entity.OrderItem;
import com.loomi.ecommerce.entity.Product;
import com.loomi.ecommerce.exeception.InsufficientStockException;
import com.loomi.ecommerce.exeception.ProductNotFoundException;
import com.loomi.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@SecurityRequirement(name = "Bearer Authentication")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Find all Product", tags = "Product")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @Operation(summary = "Find Product by ID", tags = "Product")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @GetMapping(path = "/{product_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Product> findById(@PathVariable(name = "product_id") Long id) {
        Product product = productService.findById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
    }

    @Operation(summary = "Find OrderItem by Product ID", tags = "Product")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @GetMapping(path = "/orderitem/{product_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OrderItem>> findOrderItemByProjectId(@PathVariable(name = "product_id") Long id) {
        List<OrderItem> listOrderItem = productService.findOrderItemByProjectId(id);
        if (listOrderItem == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(listOrderItem, HttpStatus.OK);
        }
    }

    @Operation(summary = "Find Product by name", tags = "Product")
    @GetMapping(path = "/name/{product_name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Product>> findByName(@PathVariable(name = "product_name") String name) {
        return ResponseEntity.ok(productService.findByProductName(name));
    }

    @Operation(summary = "Find Product by category", tags = "Product")
    @GetMapping(path = "/category/{product_category}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Product>> findByCategory(@PathVariable(name = "product_category") String category) {
        return ResponseEntity.ok(productService.findByCategory(category));
    }

    @Operation(summary = "Find Product by description", tags = "Product")
    @GetMapping(path = "/description/{product_description}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Product>> findByDescription(@PathVariable(name = "product_description") String description) {
        return ResponseEntity.ok(productService.findByDescription(description));
    }

    @Operation(summary = "Find Available Products", tags = "Product")
    @GetMapping(path = "/availableproducts", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Product>> availableProducts() {
        return ResponseEntity.ok(productService.availableProducts());
    }

    @Operation(summary = "Find Product by price", tags = "Product")
    @GetMapping(path = "/price/{product_price}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Product>> findByPrice(@PathVariable(name = "product_price") double price) {
        return ResponseEntity.ok(productService.findByPrice(price));
    }

    @Operation(summary = "Find Product by quantity in stock", tags = "Product")
    @GetMapping(path = "/quantityinstock/{product_quantity_in_stock}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Product>> findByQuantityInStock(@PathVariable(name = "product_quantity_in_stock")
                                                               int quantityInStock) {
        return ResponseEntity.ok(productService.findByQuantityInStock(quantityInStock));
    }

    @Operation(summary = "Save a new Product", tags = "Product")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Product> save(@RequestBody Product product) {
        try {
            product = productService.save(product);
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Update a Product", tags = "Product")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @PutMapping(path = "/{product_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Product> update(@PathVariable(name = "product_id") Long id, @RequestBody Product product) {
        product.setId(id);
        try {
            product = productService.update(product);
            if (product == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(product, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Remove Product Quantity", tags = "Product")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @PutMapping(path = "removeproduct/{quantity}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Product> removeProductByQuantity(@PathVariable(name = "quantity") int quantity, @RequestBody Product product) {
        product.setId(product.getId());
        try {
            product = productService.removeProductByQuantity(quantity, product);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (InsufficientStockException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete a Product", tags = "Product")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "No Content")})
    @DeleteMapping(path = "/{product_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteById(@PathVariable(name = "product_id") Long id) {
        productService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
