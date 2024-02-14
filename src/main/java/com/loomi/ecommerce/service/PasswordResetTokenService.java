package com.loomi.ecommerce.service;

import com.loomi.ecommerce.entity.DAO.PasswordResetToken;
import com.loomi.ecommerce.entity.User;
import com.loomi.ecommerce.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PasswordResetTokenService {
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private UserService userService;

    public PasswordResetToken save(PasswordResetToken passwordResetToken) {
        passwordResetToken.setId(null);
        return passwordResetTokenRepository.save(passwordResetToken);
    }
    public PasswordResetToken findByToken(String token) {
        Optional<PasswordResetToken> optionalToken =
                passwordResetTokenRepository.findByToken(token);
        return optionalToken.orElse(null);
    }

    public PasswordResetToken findById(Long id) {
        Optional<PasswordResetToken> optionalPasswordResetToken = passwordResetTokenRepository.findById(id);
        return optionalPasswordResetToken.orElse(null);
    }

    public PasswordResetToken update(PasswordResetToken passwordResetToken) {
        PasswordResetToken passwordResetTokenFound = findById(passwordResetToken.getId());
        if (passwordResetTokenFound != null) {
            return passwordResetTokenRepository.save(passwordResetToken);
        } else {
            return passwordResetToken;
        }
    }

    public void deleteById(Long id) {
        PasswordResetToken passwordResetTokenFound = new PasswordResetToken();
        passwordResetTokenFound.setId(id);
        passwordResetTokenRepository.delete(passwordResetTokenFound);

    }

    public User setPasswordForToken(String newPassword, String token) {
        Optional<PasswordResetToken> optionalToken =
                passwordResetTokenRepository.findByToken(token);
        if(optionalToken.isPresent()){
            if(LocalDateTime.now().isBefore(optionalToken.get().getDateValidation())){
                User user = userService.findById(optionalToken.get().getUserId());
                user.setPassword(newPassword);
                userService.update(user);
                return user;
            }else {
                return null;
            }
        }
        return null;
    }


}
