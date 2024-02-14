package com.loomi.ecommerce.service;

import com.loomi.ecommerce.entity.PasswordResetToken;
import com.loomi.ecommerce.entity.User;
import com.loomi.ecommerce.entity.UserType;
import com.loomi.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private PasswordResetTokenService passwordResetTokenService;
    @Autowired
    private EmailService emailService;
    @Value("${url.default}")
    private String urlDefault;
    @Value("${email.send.create.user.subject}")
    private String emailSubject;
    @Value("${email.send.create.user.message}")
    private String emailMessage;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        user.setId(null);
        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setType(UserType.CUSTOMER);
        return userRepository.save(user);
    }

    public User saveSendEmail(User user) {
        user.setId(null);
        String encryptedPassword = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
        user.setPassword(encryptedPassword);
        user.setType(UserType.CUSTOMER);
        userRepository.save(user);

        PasswordResetToken passwordResetToken = new PasswordResetToken(user.getId());
        passwordResetToken.setValidationDate(24);
        passwordResetToken = passwordResetTokenService.save(passwordResetToken);

        if (passwordResetToken != null) {
            String message = emailMessage.toString() + " " + urlDefault + "/passwordresettoken/reset"
                    + " token: " + passwordResetToken.getToken();
            emailService.sendEmail(user.getEmail(), emailSubject, message);
        }
        return user;
    }

    public User findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    public List<User> findByName(String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }

    public List<User> findByEmail(String name) {
        return userRepository.findByEmailContainingIgnoreCase(name);
    }

    public User update(User user) {
        User userFound = findById(user.getId());
        if (userFound != null) {
            return userRepository.save(user);
        } else {
            return user;
        }
    }

    public User update(User user, User authenticatedUser) {
        if (authenticatedUser.isAdmin()) {
            updateAdmin(user);
        } else if (authenticatedUser.getId().equals(user.getId())) {
            updateSimpleUser(user);
        }
        return user;
    }

    public void deleteById(Long id) {
        User user = new User();
        user.setId(id);
        userRepository.delete(user);

    }

    public UserDetails findByLogin(String email) {
        return userRepository.findByEmail(email);
    }

    private User updateSimpleUser(User user) {
        User userFound = findById(user.getId());
        if (userFound != null) {
            userFound.setName(user.getName());
            String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
            userFound.setPassword(encryptedPassword);
            return userRepository.save(userFound);
        } else {
            return user;
        }
    }

    private User updateAdmin(User user) {
        User userFound = findById(user.getId());
        if (userFound != null) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
            user.setPassword(encryptedPassword);
            return userRepository.save(user);
        } else {
            return user;
        }
    }
}
