package com.api.store.controller;

import com.api.store.dto.product.AddProductDto;
import com.api.store.model.entities.mysql.Product;
import com.api.store.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    void addProduct(@RequestBody @Valid  AddProductDto data) {
        Product product = new Product();
        product.setName(data.name);
        product.setLogin(data.login);
        product.setPassword(data.password);

        this.productService.save(product);
    }

    @RequestMapping
    List<Product> getAllProducts() {
        return this.productService.getAll();
    }

    @RequestMapping(value = "/{id}")
    Product getProductById(@PathVariable("id") String id) {
        return this.productService.getById(id);
    }
}
