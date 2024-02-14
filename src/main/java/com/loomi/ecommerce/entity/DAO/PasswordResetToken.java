package com.loomi.ecommerce.entity.DAO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;
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
    private Timestamp dateCreated;
    private Timestamp dateValidation;

    private boolean isActivated;

    public PasswordResetToken(Long userId) {
        this.userId = userId;
        this.token = generateRandomToken();
        this.dateCreated = new Timestamp(System.currentTimeMillis());
        this.isActivated = true;
    }

    public void setValidationDate(int hours) {
        long millisecondsToAdd = hours * 60 * 60 * 1000;
        this.dateValidation = new Timestamp(this.dateCreated.getTime() + millisecondsToAdd);
    }

    private String generateRandomToken() {
        return UUID.randomUUID().toString();
    }
}
