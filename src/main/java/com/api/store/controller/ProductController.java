package com.api.store.controller;

import com.api.store.dto.product.AddProductDto;
import com.api.store.model.entities.mysql.Product;
import com.api.store.service.ProductService;
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
    void addProduct(@RequestBody AddProductDto data) {
        Product product = new Product();
        product.setName(data.name);
        product.setPrice(data.price);
        product.setStock(data.stock);

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
