package com.loomi.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"client\"")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_sq")
    @SequenceGenerator(name = "client_sq", sequenceName = "client_sq", initialValue = 1, allocationSize = 1)
    private Long id;
    private String fullName;
    private String contact;
    private String address;
    private boolean isActive;

    @Column(name = "creation_date", nullable = false)
    @CreationTimestamp
    private Timestamp creationDate;

    @Column(name = "update_date")
    private Timestamp updateDate;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

}
