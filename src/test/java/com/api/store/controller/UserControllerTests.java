package com.api.store.controller;

import com.api.store.dto.user.request.AddUserRequestDto;
import com.api.store.dto.user.request.EditUserRequestDto;
import com.api.store.model.entities.mongodb.User;
import com.api.store.service.MigrationService;
import com.api.store.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
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

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        sut = new UserController(userService, migrationService);
    }


    @Test
    @DisplayName("should call user service with correct values")
    void AddUser() {
        AddUserRequestDto dto = new AddUserRequestDto("Test", "teste@gmail.com", "StrongPassword@123");
        sut.addUser(dto);

        User user = new User();
        user.setName(dto.name());
        user.setLogin(dto.login());
        user.setPassword(dto.password());

        Mockito.verify(userService, Mockito.times(1)).save(ArgumentMatchers.any(User.class));
        Mockito.verify(userService, Mockito.times(1)).save(ArgumentMatchers.refEq(user));
    }

    @Test
    @DisplayName("should call getAttribute with correct values and return user")
    void getUserById() {
        Mockito.when(request.getAttribute(ArgumentMatchers.anyString())).thenReturn("fake-user-id");
        User userInput = new User();
        Mockito.when(userService.getById(ArgumentMatchers.anyString())).thenReturn(userInput);
        User userOutput = sut.getUserById(request);

        Mockito.verify(request, Mockito.times(1)).getAttribute("userId");
        Assertions.assertEquals(userInput, userOutput);
    }

    @Test
    @DisplayName("should call getAttributeRequest with correct values and user service")
    void deleteUserById() {
        Mockito.when(request.getAttribute(ArgumentMatchers.anyString())).thenReturn("fake-user-id");
        sut.deleteUserById(request);

        Mockito.verify(request, Mockito.times(1)).getAttribute("userId");
        Mockito.verify(userService, Mockito.times(1)).deleteById("fake-user-id");
    }

    @Test
    @DisplayName("should call getAttribute with correct value")
    void editUserById() {
        Mockito.when(request.getAttribute(ArgumentMatchers.anyString())).thenReturn("fake-user-id");

        EditUserRequestDto dto = new EditUserRequestDto("Test", "teste@gmail.com", "StrongPassword@123");
        sut.editUserById(request, dto);

        User user = new User();
        user.setId("fake-user-id");
        user.setName(dto.name());
        user.setLogin(dto.login());
        user.setPassword(dto.password());

        Mockito.verify(request, Mockito.times(1)).getAttribute("userId");
        Mockito.verify(userService, Mockito.times(1)).editById(ArgumentMatchers.refEq(user));
    }
}
