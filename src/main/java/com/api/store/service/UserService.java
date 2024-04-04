package com.api.store.service;

import com.api.store.infra.database.mysql.repositories.MysqlUserRepository;
import com.api.store.model.entities.mysql.User;
import com.api.store.utils.errors.BcryptConfig;
import com.api.store.utils.errors.GenericError;
import com.api.store.utils.errors.InvalidParamError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final MysqlUserRepository mysqlUserRepository;

    @Autowired
    public UserService(MysqlUserRepository mysqlUserRepository) {
        this.mysqlUserRepository = mysqlUserRepository;
    }

    public void save(User user) {
        Optional<User> userOptional = this.mysqlUserRepository.findByLogin(user.getLogin());
        if (userOptional.isPresent()) throw new GenericError("User already exists");

        user.setPassword(BcryptConfig.hash(user.getPassword()));

        this.mysqlUserRepository.save(user);
    }

    public List<User> getAll() {
        return this.mysqlUserRepository.findAll();
    }

    public User getById(String id) {
        Optional<User> optionalUser = this.mysqlUserRepository.findById(UUID.fromString(id));

        return optionalUser.orElse(null);
    }

    public void deleteById(String id) {
        this.mysqlUserRepository.deleteById(UUID.fromString(id));
    }

    public void editById(User user) {
        Optional<User> userByIdOptional = this.mysqlUserRepository.findById(user.getId());
        if (userByIdOptional.isEmpty()) throw new InvalidParamError("user_id");

        Optional<User> userByEmailOptional = this.mysqlUserRepository.findByLogin(user.getLogin());
        if (userByEmailOptional.isPresent() && Objects.equals(userByEmailOptional.get().getLogin(), userByIdOptional.get().getLogin())) {
            throw new GenericError("User already exists");
        }

        boolean isPasswordUpdated = user.getPassword().equals(userByIdOptional.get().getPassword());
        if (isPasswordUpdated) {
            user.setPassword(BcryptConfig.hash(user.getPassword()));
        }

        this.mysqlUserRepository.save(user);
    }
}
