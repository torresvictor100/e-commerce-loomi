package com.loomi.ecommerce.repository;

import com.loomi.ecommerce.entity.User;
import com.loomi.ecommerce.entity.UserType;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;
    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    @Test
    @DisplayName("find user by id sucesso")
    void findUserByIdSucessoFound(){

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        User user = new User(1L, "jaoaj", "lala@mgail.com", "123", timestamp, timestamp, UserType.ADMIN, null);
        User savedUser = userRepository.save(user);

        Optional<User> result = userRepository.findById(savedUser.getId());
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("find user by id false")
    void findUserByIdSucessoNoFound(){

        Optional<User> result = userRepository.findById(1L);
        assertThat(result.isPresent()).isFalse();

    }


}


