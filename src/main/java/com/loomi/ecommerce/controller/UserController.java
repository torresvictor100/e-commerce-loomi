package com.loomi.ecommerce.controller;

import com.loomi.ecommerce.entity.DTO.RegisterDTO;
import com.loomi.ecommerce.entity.User;
import com.loomi.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Find all Users", tags = "User")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @Operation(summary = "Find Users by name", tags = "User")
    @GetMapping(path = "/name/{user_name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<User>> findByName(@PathVariable(name = "user_name") String name) {
        return ResponseEntity.ok(userService.findByName(name));
    }

    @Operation(summary = "Find Users by email", tags = "User")
    @GetMapping(path = "/email/{user_email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<User>> findByEmail(@PathVariable(name = "user_email") String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @Operation(summary = "Find User by ID", tags = "User")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @GetMapping(path = "/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> findById(@PathVariable(name = "user_id") Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @Operation(summary = "Update a User", tags = "User")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @PutMapping(path = "/id/{user_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> update(@PathVariable(name = "user_id") Long id,
                                       @RequestBody User user,
                                       Authentication authentication) {
        user.setId(id);
        User authenticatedUser = (User) authentication.getPrincipal();
        try {
            user = userService.update(user, authenticatedUser);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete a User", tags = "User")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "No Content")})
    @DeleteMapping(path = "/{user_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteById(@PathVariable(name = "user_id") Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/register")
    @Operation(summary = "register new user", tags = "User")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(this.userService.findByLogin(data.email()) != null) return ResponseEntity.badRequest().build();

        User newUser = new User(data.name(), data.email());
        this.userService.saveSendEmail(newUser);
        return ResponseEntity.ok().build();
    }
}
