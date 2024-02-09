package com.loomi.ecommerce.service;

import com.loomi.ecommerce.entity.User;
import com.loomi.ecommerce.entity.UserType;

import com.loomi.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private UserService userService;

    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @BeforeEach
    void setUo() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Test save method in UserService")
    void save() {

        User userToSave = new User();
        userToSave.setName("John Doe");
        userToSave.setEmail("john.doe@example.com");
        userToSave.setPassword("123");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("John Doe");
        savedUser.setEmail("john.doe@example.com");
        userToSave.setPassword("123");
        userToSave.setCreationDate(timestamp);
        userToSave.setCreationDate(timestamp);
        userToSave.setType(UserType.ADMIN);
        userToSave.setClient(null);

        when(userRepository.save(userToSave)).thenReturn(savedUser);

        User result = userService.save(userToSave);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("John Doe");
        assertThat(result.getEmail()).isEqualTo("john.doe@example.com");
        userToSave.setCreationDate(timestamp);
        userToSave.setCreationDate(timestamp);
        userToSave.setType(UserType.ADMIN);
        userToSave.setClient(null);

    }

}