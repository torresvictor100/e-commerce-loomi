package com.loomi.ecommerce.repository;

import com.loomi.ecommerce.entity.Product;
import com.loomi.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByProductNameContainingIgnoreCase(String productName);

    List<Product> findByCategoryContainingIgnoreCase(String productName);

    List<Product> findByDescriptionContainingIgnoreCase(String description);

    List<Product> findByPrice(double price);

    List<Product> findByQuantityInStock(int quantityInStock);

    List<Product> findByQuantityInStockGreaterThan(int quantity);
}
