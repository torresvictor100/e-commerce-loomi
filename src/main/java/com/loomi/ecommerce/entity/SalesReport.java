package com.loomi.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
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

    public Timestamp getPeriod() {
        return period;
    }

    public void setPeriod(Timestamp period) {
        this.period = period;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }

    public int getProductsSold() {
        return productsSold;
    }

    public void setProductsSold(int productsSold) {
        this.productsSold = productsSold;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
