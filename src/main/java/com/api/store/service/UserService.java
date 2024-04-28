package com.api.store.service;

import com.api.store.infra.database.mongodb.repositories.MongoUserRepository;
import com.api.store.model.entities.mongodb.User;
import com.api.store.utils.encryption.BcryptConfig;
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
    private final MongoUserRepository mongoRepository;

    @Autowired
    public UserService(MongoUserRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    public void save(User user) {
        Optional<User> userOptional = this.mongoRepository.findByLogin(user.getLogin());
        if (userOptional.isPresent()) throw new GenericError("User already exists");

        user.setPassword(BcryptConfig.hash(user.getPassword()));

        this.mongoRepository.save(user);
    }

    public List<User> getAll() {
        return this.mongoRepository.findAll();
    }

    public User getById(String id) {
        Optional<User> optionalUser = this.mongoRepository.findById(id);

        return optionalUser.orElse(null);
    }

    public void deleteById(String id) {
        this.mongoRepository.deleteById(id);
    }

    public void editById(User user) {
        Optional<User> userByIdOptional = this.mongoRepository.findById(user.getId());
        if (userByIdOptional.isEmpty()) throw new InvalidParamError("user_id");

        Optional<User> userByEmailOptional = this.mongoRepository.findByLogin(user.getLogin());
        if (userByEmailOptional.isPresent() && !Objects.equals(userByEmailOptional.get().getLogin(), userByIdOptional.get().getLogin())) {
            throw new GenericError("User already exists");
        }

        boolean isPasswordUpdated = !BcryptConfig.verifyHash(user.getPassword(), userByIdOptional.get().getPassword());
        if (isPasswordUpdated) {
            user.setPassword(BcryptConfig.hash(user.getPassword()));
        } else {
            user.setPassword(userByIdOptional.get().getPassword());
        }

        this.mongoRepository.save(user);
    }

    public User getByLogin(String login) {
        Optional<User> userOptional = this.mongoRepository.findByLogin(login);
        if (userOptional.isEmpty()) throw new InvalidParamError("login");
        return userOptional.get();
    }

}
