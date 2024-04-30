package com.api.store.controller;

import com.api.store.dto.user.request.AddUserRequestDto;
import com.api.store.model.entities.mongodb.User;
import com.api.store.service.MigrationService;
import com.api.store.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserControllerTests {
    @InjectMocks
    private UserController sut;

    @Mock
    UserService userService;

    @Mock
    MigrationService migrationService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        sut = new UserController(userService, migrationService);
    }


    @Test
    @DisplayName("should call user service with correct values")
    void CreateUser() {
        AddUserRequestDto dto = new AddUserRequestDto("Test", "teste@gmail.com", "StrongPassword@123");
        sut.addUser(dto);

        User user = new User();
        user.setName(dto.name());
        user.setLogin(dto.login());
        user.setPassword(dto.password());

        Mockito.verify(userService, Mockito.times(1)).save(ArgumentMatchers.any(User.class));
        Mockito.verify(userService, Mockito.times(1)).save(ArgumentMatchers.refEq(user));
    }
}
