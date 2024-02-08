package com.loomi.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"order_item_shopping_cart\"")
public class OrderItemShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item__shopping_cart_sq")
    @SequenceGenerator(name = "order_item_shopping_cart_sq", sequenceName = "order_item_shopping_cart_sq", initialValue = 1, allocationSize = 1)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "shopping_cart_id", nullable = false)
    private Long shoppingCartId;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToOne(optional = false)
    @JoinColumn(name = "shopping_cart_id", nullable = false, insertable = false, updatable = false)
    @JsonIgnoreProperties({"orderItemsShoppingCart"})
    private ShoppingCart shoppingCart;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false, insertable = false, updatable = false)
    @JsonIgnoreProperties({"orderItemsShoppingCart"})
    private Product product;

    private int quantity;

    private BigDecimal unitPrice;

    @Transient
    private BigDecimal subtotal;

    @PostLoad
    public void calculateSubtotal() {
        this.subtotal = this.unitPrice.multiply(BigDecimal.valueOf(this.quantity));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
