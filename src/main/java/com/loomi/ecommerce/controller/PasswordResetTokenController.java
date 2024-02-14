package com.loomi.ecommerce.controller;


import com.loomi.ecommerce.entity.DTO.TokenRequestDTO;
import com.loomi.ecommerce.entity.User;
import com.loomi.ecommerce.service.PasswordResetTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passwordresettoken")
public class PasswordResetTokenController {
    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Operation(summary = "Update a new PasswordBy token", tags = "PasswordResetToken")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    @PutMapping(path = "/reset", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> update(@RequestBody TokenRequestDTO tokenRequestDTO) {
        try {
            User user = passwordResetTokenService.setPasswordForToken(tokenRequestDTO.newPassword(), tokenRequestDTO.token());
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
