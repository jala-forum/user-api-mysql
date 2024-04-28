package com.api.store.controller;

import com.api.store.dto.user.request.AddUserRequestDto;
import com.api.store.dto.user.request.EditUserRequestDto;
import com.api.store.model.entities.mongodb.User;
import com.api.store.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
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
    void addUser(@RequestBody @Valid AddUserRequestDto data) {
        User user = new User();
        user.setName(data.name());
        user.setLogin(data.login());
        user.setPassword(data.password());

        this.userService.save(user);
    }

    @RequestMapping()
    User getUserById(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return this.userService.getById(userId);
    }

    @DeleteMapping()
    void deleteUserById( HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        this.userService.deleteById(userId);
    }

    @PutMapping()
    void editUserById(HttpServletRequest request, @RequestBody() @Valid EditUserRequestDto data) {
        String userId = (String) request.getAttribute("userId");
        User user = new User();
        user.setId(userId);
        user.setLogin(data.login());
        user.setName(data.name());
        user.setPassword(data.password());

        this.userService.editById(user);
    }

    @RequestMapping("/migration")
    List<User> migration() {
        return this.userService.getAll();
    }
}
