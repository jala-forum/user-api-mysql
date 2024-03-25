package com.api.store.dto.product;

import lombok.Setter;

@Setter
public class AddProductDto {
    public String name;

    public int price;

    public int stock;
}
