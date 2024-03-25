package com.api.store.dto.product;

import jakarta.validation.constraints.Min;
import lombok.Setter;

@Setter
public class AddProductDto {
    public String name;

    @Min(0)
    public int price;

    @Min(0)
    public int stock;
}
