package com.loomi.ecommerce.repository;

import com.loomi.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  ProductRepository extends JpaRepository<Product, Long> {
}
