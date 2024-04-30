package com.api.store.service;

import com.api.store.controller.UserController;
import com.api.store.infra.database.mongodb.repositories.MongoUserRepository;
import com.api.store.model.entities.mongodb.User;
import com.api.store.utils.errors.GenericError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @InjectMocks
    UserService sut;

    @Mock
    MongoUserRepository userRepository;

    User user = new User();

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        sut = new UserService(userRepository);
        user.setLogin("fake-login");
        user.setPassword("fake-password");
        user.setName("fake-name");
    }

    @Test
    @DisplayName("should call findByLogin with correct values")
    void save_MethodFindByLogin() {
        sut.save(user);

        Mockito.verify(userRepository).findByLogin(user.getLogin());
    }

    @Test
    @DisplayName("should return an error if user already exists")
    void save_IfUserAlreadyExists() {
        Optional<User> userOptional = Optional.of(user);
        Mockito.when(userRepository.findByLogin(ArgumentMatchers.anyString())).thenReturn(userOptional);

        GenericError error = Assertions.assertThrows(GenericError.class, () -> {
            sut.save(user);
        });

        Assertions.assertEquals("User already exists", error.getMessage());
    }
}
