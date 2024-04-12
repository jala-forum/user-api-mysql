package com.api.store.dto.authentication;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

public record HashDto(@Email String login, @Length(min = 8, max = 100) String password) { }
