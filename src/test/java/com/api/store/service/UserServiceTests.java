package com.api.store.service;

import com.api.store.infra.database.mongodb.repositories.MongoUserRepository;
import com.api.store.model.entities.mongodb.User;
import com.api.store.utils.encryption.BcryptConfig;
import com.api.store.utils.errors.GenericError;
import com.api.store.utils.errors.InvalidParamError;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @InjectMocks
    UserService sut;

    @Mock
    MongoUserRepository userRepository;

    User user = new User();

    private MockedStatic<BcryptConfig> mockStatic;


    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        sut = new UserService(userRepository);
        user.setId("fake-id");
        user.setLogin("fake-login");
        user.setPassword("fake-password");
        user.setName("fake-name");

        mockStatic = Mockito.mockStatic(BcryptConfig.class);
    }

    @AfterEach
    public void afterEach() {
        mockStatic.close();
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
        Optional<User> optionalUser = Optional.of(user);
        Mockito.when(userRepository.findByLogin(ArgumentMatchers.anyString())).thenReturn(optionalUser);

        GenericError error = Assertions.assertThrows(GenericError.class, () -> {
            sut.save(user);
        });

        Assertions.assertEquals("User already exists", error.getMessage());
    }

    @Test
    @DisplayName("should call save from mongo repository with correct values")
    public void save_SaveMethodWithCorrectValues() {
        sut.save(user);

        Mockito.verify(userRepository).save(user);
    }

    @Test
    @DisplayName("should return all users")
    public void getAll_ReturnAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user);

        Mockito.when(userRepository.findAll()).thenReturn(users);
        List<User> sutUsers = sut.getAll();

        Assertions.assertEquals(users, sutUsers);
    }

    @Test
    @DisplayName("should call findById method with correct values")
    void getById_CallFindByIdWithCorrectValues() {
        sut.getById(user.getId());

        Mockito.verify(userRepository).findById(user.getId());
    }

    @Test
    @DisplayName("should return user if success")
    void getById_ReturnUserIfSuccess() {
        Optional<User> optionalUser = Optional.of(user);
        Mockito.when(userRepository.findById(ArgumentMatchers.anyString())).thenReturn(optionalUser);

        User sutUser = sut.getById(user.getId());

        Assertions.assertEquals(user, sutUser);
    }

    @Test
    @DisplayName("should call deleteById with correct values")
    void deleteById_CallDeleteByIdWithCorrectValues() {
        sut.deleteById(user.getId());

        Mockito.verify(userRepository).deleteById(user.getId());
    }

    @Test
    @DisplayName("should call findById with correct values")
    void editById_CallFindByIdWithCorrectValues() {
        Optional<User> optionalUser = Optional.of(user);
        Mockito.when(userRepository.findById(ArgumentMatchers.anyString())).thenReturn(optionalUser);

        sut.editById(user);

        Mockito.verify(userRepository).findById(user.getId());
    }

    @Test
    @DisplayName("should throw if userByIdOptional is empty")
    void editById_ThrowIfUserByIdOptionalIsEmpty() {
        Optional<User> optionalUser = Optional.empty();
        Mockito.when(userRepository.findById(ArgumentMatchers.anyString())).thenReturn(optionalUser);

        InvalidParamError error = Assertions.assertThrows(InvalidParamError.class, () -> {
            sut.editById(user);
        });

        Assertions.assertEquals("Invalid param user_id", error.getMessage());
    }

    @Test
    @DisplayName("should call findByLogin with correct values")
    void editById_CallFindByLoginWithCorrectValues() {
        Optional<User> optionalUser = Optional.of(user);
        Mockito.when(userRepository.findById(ArgumentMatchers.anyString())).thenReturn(optionalUser);

        sut.editById(user);

        Mockito.verify(userRepository).findByLogin(user.getLogin());
    }

    @Test
    @DisplayName("should throw an error if already exists an user with this login")
    void editById_ThrowAnErrorIfLoginAlreadyExists() {
        Optional<User> optionalUser = Optional.of(user);
        Mockito.when(userRepository.findById(ArgumentMatchers.anyString())).thenReturn(optionalUser);

        User userForEmailOptional = new User();
        BeanUtils.copyProperties(user, userForEmailOptional);
        userForEmailOptional.setLogin("fake-email");
        Optional<User> userByEmailOptional = Optional.of(userForEmailOptional);
        Mockito.when(userRepository.findByLogin(ArgumentMatchers.anyString())).thenReturn(userByEmailOptional);

        GenericError error = Assertions.assertThrows(GenericError.class, () -> {
            sut.editById(user);
        });

        Assertions.assertEquals("User already exists", error.getMessage());
    }

    @Test
    @DisplayName("should call mongo repository save method with correct value")
    void editById_CallSaveMethodWithCorrectValue() {
        Optional<User> emptyOptionalUser = Optional.empty();
        Mockito.when(userRepository.findByLogin(ArgumentMatchers.anyString())).thenReturn(emptyOptionalUser);

        Optional<User> optionalUser = Optional.of(user);
        Mockito.when(userRepository.findById(ArgumentMatchers.anyString())).thenReturn(optionalUser);

        sut.editById(user);

        Mockito.verify(userRepository).save(user);
    }

    @Test
    @DisplayName("should call findByLogin with correct value")
    void getByLogin_CallFindByLoginWithCorrectValue() {
        Optional<User> userOptional = Optional.of(user);
        Mockito.when(userRepository.findByLogin(ArgumentMatchers.anyString())).thenReturn(userOptional);

        sut.getByLogin(user.getLogin());

        Mockito.verify(userRepository).findByLogin(user.getLogin());
    }
}
