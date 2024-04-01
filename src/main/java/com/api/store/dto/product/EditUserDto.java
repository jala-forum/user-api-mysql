package com.api.store.dto.product;


import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

public record EditUserDto (
        @Length(min = 1)
        String name,
        @Email()
        String login,
        @Length(min = 8)
        String password
) {};
