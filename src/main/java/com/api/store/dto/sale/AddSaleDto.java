package com.api.store.dto.sale;

import com.api.store.model.entities.mysql.Product;
import jakarta.validation.constraints.Min;

public class AddSaleDto {
    public String productId;

    @Min(1)
    public int quantity;
}
