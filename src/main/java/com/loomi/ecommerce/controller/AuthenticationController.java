package com.loomi.ecommerce.controller;

import com.loomi.ecommerce.entity.DTO.AuthenticationDTO;
import com.loomi.ecommerce.entity.DTO.LoginResponseDTO;
import com.loomi.ecommerce.entity.DTO.RegisterDTO;
import com.loomi.ecommerce.entity.User;
import com.loomi.ecommerce.security.TokenService;
import com.loomi.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;


    @Operation(summary = "login Authentication", tags = "Authentication")
    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    @Operation(summary = "register new user", tags = "Authentication")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(this.userService.findByLogin(data.email()) != null) return ResponseEntity.badRequest().build();

        User newUser = new User(data.name(), data.email(), data.password(), data.type());
        this.userService.save(newUser);
        return ResponseEntity.ok().build();
    }


}
