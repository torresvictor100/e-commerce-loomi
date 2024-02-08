package com.loomi.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "\"sales_report\"")
public class SalesReport {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_report_sq")
    @SequenceGenerator(name = "sales_report_sq", sequenceName = "sales_report_sq", initialValue = 1, allocationSize = 1)
    private Long id;

    private Timestamp period;

    private BigDecimal totalSales;

    private int productsSold;

    private String filePath;

}
