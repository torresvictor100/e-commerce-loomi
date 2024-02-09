package com.loomi.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"sales_report\"")
public class SalesReport {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_report_sq")
    @SequenceGenerator(name = "sales_report_sq", sequenceName = "sales_report_sq", allocationSize = 1)
    private Long id;

    @NotNull
    private Timestamp period;

    @NotNull
    @Positive
    private BigDecimal totalSales;

    @Positive
    private int productsSold;

    private String filePath;

}
