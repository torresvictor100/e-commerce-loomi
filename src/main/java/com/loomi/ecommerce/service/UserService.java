package com.loomi.ecommerce.service;

import com.loomi.ecommerce.entity.User;
import com.loomi.ecommerce.entity.UserType;
import com.loomi.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

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
        User userFound = findById(user.getId());
        if (authenticatedUser.isAdmin()) {
            updateAdmin(user);
        }else if (authenticatedUser.getId().equals(user.getId())){
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
            userFound.setPassword(user.getPassword());
            return userRepository.save(user);
        } else {
            return user;
        }
    }

    private User updateAdmin(User user) {
        User userFound = findById(user.getId());
        if (userFound != null) {
            return userRepository.save(user);
        } else {
            return user;
        }
    }

}
