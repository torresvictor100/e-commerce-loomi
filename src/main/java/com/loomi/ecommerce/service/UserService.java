package com.loomi.ecommerce.service;

import com.loomi.ecommerce.entity.User;
import com.loomi.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User save(User user){

        user.setId(null);
        user.setPassword("123");
        return userRepository.save(user);
    }

    public User findById(Long id) {
        Optional<User> optionalUser=  userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    public List<User> findByName(String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }

    public List<User> findByEmail(String name) {
        return userRepository.findByEmailContainingIgnoreCase(name);
    }

    public User update(User user) {
        User  userFound = findById(user.getId());
        if (userFound != null) {
            return userRepository.save(user);
        }else{
            return user;
        }
    }

    public void deleteById(Long id) {
        User user = new User();
        user.setId(id);
        userRepository.delete(user);

    }

}
