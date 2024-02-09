package com.loomi.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"client\"")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_sq")
    @SequenceGenerator(name = "client_sq", sequenceName = "client_sq", allocationSize = 1)
    private Long id;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    @JsonIgnoreProperties({"client"})
    private User user;

    @NotNull
    private String fullName;

    @Pattern(regexp = "[0-9]+")
    @Size(min = 10, max = 15)
    private String contact;

    @NotBlank
    private String address;

    private boolean isActive;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private List<Order> orders;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private List<ShoppingCart> shoppingCart;

    @NotNull
    @CreationTimestamp
    private Timestamp creationDate;
    private Timestamp updateDate;

}
