package com.api.store.controller;

import com.api.store.dto.product.AddProductDto;
import com.api.store.model.entities.mysql.User;
import com.api.store.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final UserService userService;

    @Autowired
    public ProductController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    void addProduct(@RequestBody @Valid  AddProductDto data) {
        User product = new User();
        product.setName(data.name);
        product.setLogin(data.login);
        product.setPassword(data.password);

        this.userService.save(product);
    }

    @RequestMapping
    List<User> getAllProducts() {
        return this.userService.getAll();
    }

    @RequestMapping(value = "/{id}")
    User getProductById(@PathVariable("id") String id) {
        return this.userService.getById(id);
    }

    @DeleteMapping(value = "/{id}")
    void deleteUserById(@PathVariable("id") String id) {
        this.userService.deleteById(id);
    }
}
