package com.api.store.dto.product;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Setter;

@Setter
public class AddProductDto {
    @Min(1)
    public String name;

    @Min(0)
    @Email
    public String login;

    @Min(8)
    @Max(100)
    public String password;
}
