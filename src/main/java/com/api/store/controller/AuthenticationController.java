package com.api.store.controller;

import com.api.store.dto.authentication.HashDto;
import com.api.store.model.entities.mysql.User;
import com.api.store.service.UserService;
import com.api.store.utils.errors.BcryptConfig;
import com.api.store.utils.errors.InvalidParamError;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    public String hash(@RequestBody @Valid HashDto data) {
        // User userByEmail = this.userService.getByLogin(data.login());

        // boolean isPasswordOk = BcryptConfig.verifyHash(data.password(), userByEmail.getPassword());
        // if (!isPasswordOk) throw new InvalidParamError("password");

        // gerar o token de acesso

        return "Bearer accessToken";
    }
}
