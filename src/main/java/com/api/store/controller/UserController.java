package com.api.store.controller;

import com.api.store.dto.user.request.AddUserDto;
import com.api.store.dto.user.request.EditUserDto;
import com.api.store.model.entities.mysql.User;
import com.api.store.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    void addUser(@RequestBody @Valid AddUserDto data) {
        User user = new User();
        user.setName(data.name());
        user.setLogin(data.login());
        user.setPassword(data.password());

        this.userService.save(user);
    }

    @RequestMapping
    List<User> getAllUsers() {
        return this.userService.getAll();
    }

    @RequestMapping(value = "/{id}")
    User getUserById(@PathVariable("id") String id) {
        return this.userService.getById(id);
    }

    @DeleteMapping(value = "/{id}")
    void deleteUserById(@PathVariable("id") String id) {
        this.userService.deleteById(id);
    }

    @PutMapping(value = "/{id}")
    void editUserById(@PathVariable("id") String id, @RequestBody() @Valid EditUserDto data) {
        User user = new User();
        user.setId(UUID.fromString(id));
        user.setLogin(data.login());
        user.setName(data.name());
        user.setPassword(data.password());

        this.userService.editById(user);
    }
}
