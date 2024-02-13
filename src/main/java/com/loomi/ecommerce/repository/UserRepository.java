package com.loomi.ecommerce.repository;

import com.loomi.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByNameContainingIgnoreCase(String name);
    Optional<User> findByName(String name);
    List<User> findByEmailContainingIgnoreCase(String email);
    UserDetails findByEmail(String email);

}
