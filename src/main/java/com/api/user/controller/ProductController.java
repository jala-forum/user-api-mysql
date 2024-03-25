package com.api.user.controller;

import com.api.user.dto.product.AddProductDto;
import com.api.user.model.entities.mysql.Product;
import com.api.user.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    void addProduct(@RequestBody AddProductDto data) {
        Product product = new Product();
        product.setName(data.name);
        product.setPrice(data.price);
        product.setStock(data.stock);

        this.productService.save(product);
    }
}
