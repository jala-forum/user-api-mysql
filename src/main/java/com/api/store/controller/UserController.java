package com.api.store.controller;

import com.api.store.dto.product.AddUserDto;
import com.api.store.dto.product.EditUserDto;
import com.api.store.model.entities.mysql.User;
import com.api.store.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    void addProduct(@RequestBody @Valid AddUserDto data) {
        User product = new User();
        product.setName(data.name());
        product.setLogin(data.login());
        product.setPassword(data.password());

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

    @PutMapping(value = "/{id}")
    void editUserById(@PathVariable("id") String id, @RequestBody() EditUserDto data) {
        User user = new User();
        user.setId(UUID.fromString(id));
        user.setLogin(data.login());
        user.setName(data.name());
        user.setPassword(data.password());

        this.userService.editById(user);
    }
}
