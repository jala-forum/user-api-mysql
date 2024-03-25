package com.api.user.controller;

import com.api.user.dto.product.AddProductDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    @PostMapping
    void addProduct(@RequestBody AddProductDto product) {

    }
}
