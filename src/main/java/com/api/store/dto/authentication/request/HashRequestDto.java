package com.api.store.dto.authentication.request;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

public record HashRequestDto(@Email String login, @Length(min = 8, max = 100) String password) { }
