package com.loomi.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"order\"")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sq")
    @SequenceGenerator(name = "order_sq", sequenceName = "order_sq", initialValue = 1, allocationSize = 1)
    private Long id;

    private Timestamp orderDate;

    private BigDecimal totalAmount;

}
