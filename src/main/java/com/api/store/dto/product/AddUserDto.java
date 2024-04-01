package com.api.store.dto.product;

import jakarta.validation.constraints.Email;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

public record AddUserDto (
        @Length(min = 1)
        String name,
        @Email()
        String login,
        @Length(min = 6)
        String password)
{}
