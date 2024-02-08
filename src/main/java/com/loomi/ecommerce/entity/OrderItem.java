package com.loomi.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"order_item\"")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_sq")
    @SequenceGenerator(name = "order_item_sq", sequenceName = "order_item_sq", allocationSize = 1)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false, insertable = false, updatable = false)
    @JsonIgnoreProperties({"orderItems"})
    private Order order;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false, insertable = false, updatable = false)
    @JsonIgnoreProperties({"orderItems"})
    private Product product;

    private int quantity;

    private BigDecimal unitPrice;

    @Transient
    private BigDecimal subtotal;

    @PostLoad
    public void calculateSubtotal() {
        this.subtotal = this.unitPrice.multiply(BigDecimal.valueOf(this.quantity));
    }
}
