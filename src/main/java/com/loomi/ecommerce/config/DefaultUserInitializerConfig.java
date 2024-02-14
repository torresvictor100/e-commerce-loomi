package com.loomi.ecommerce.config;

import com.loomi.ecommerce.entity.User;
import com.loomi.ecommerce.entity.UserType;
import com.loomi.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserInitializerConfig implements CommandLineRunner {

    @Value("${default.user.name}")
    private String defaultUserName;
    @Value("${default.user.email}")
    private String defaultUserEmail;
    @Value("${default.user.password}")
    private String defaultUserPassword;

    private final UserRepository userRepository;

    public DefaultUserInitializerConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
            if(!userRepository.findByName(defaultUserName).isPresent()){
                User user = new User();
                user.setId(null);
                user.setName(defaultUserName);
                user.setEmail(defaultUserEmail);
                user.setPassword(new BCryptPasswordEncoder().encode(defaultUserPassword));
                user.setType(UserType.ADMIN);
                userRepository.save(user);
            }
    }
}

