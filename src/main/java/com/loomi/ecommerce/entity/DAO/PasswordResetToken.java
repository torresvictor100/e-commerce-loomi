package com.loomi.ecommerce.entity.DAO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"tokenpassword\"")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tokenpassword_sq")
    @SequenceGenerator(name = "tokenpassword_sq", sequenceName = "tokenpassword_sq", allocationSize = 1)
    private Long id;
    @NotNull
    private String token;
    @NotNull
    private Long userId;
    @NotNull
    @CreationTimestamp
    private LocalDateTime dateCreated;
    private LocalDateTime dateValidation;

    public PasswordResetToken(Long userId) {
        this.userId = userId;
        this.token = generateRandomToken();
        this.dateValidation = this.dateCreated.plusHours(24);
    }

    private String generateRandomToken() {
        return UUID.randomUUID().toString();
    }
}
