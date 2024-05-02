package com.api.store.controller;

import com.api.store.dto.authentication.request.HashRequestDto;
import com.api.store.dto.authentication.response.HashResponseDto;
import com.api.store.model.entities.mongodb.User;
import com.api.store.service.UserService;
import com.api.store.utils.encryption.JwtTokenUtil;
import com.api.store.utils.encryption.BcryptConfig;
import com.api.store.utils.errors.InvalidParamError;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @Autowired
    public AuthenticationController(JwtTokenUtil jwtTokenUtil, UserService userService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PostMapping
    public HashResponseDto hash(@RequestBody @Valid HashRequestDto data) {
         User userByEmail = this.userService.getByLogin(data.login());

         boolean isPasswordOk = BcryptConfig.verifyHash(data.password(), userByEmail.getPassword());
         if (!isPasswordOk) throw new InvalidParamError("password");

         String jwt = this.jwtTokenUtil.generateToken(userByEmail);

        return new HashResponseDto("Bearer " + jwt);
    }

    @RequestMapping("/{token}")
    public boolean validateToken(@PathVariable String token) {
        return this.jwtTokenUtil.verify(token);
    }
}
