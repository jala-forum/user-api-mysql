package com.api.store.dto.user;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

public record AddUserDto (
        @Length(min = 1, max = 100)
        String name,
        @Email()
        String login,
        @Length(min = 8, max = 100)
        String password)
{}
