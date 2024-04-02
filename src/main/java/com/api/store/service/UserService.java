package com.api.store.service;

import com.api.store.infra.database.mysql.repositories.MysqlUserRepository;
import com.api.store.model.entities.mysql.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        this.mysqlUserRepository.save(user);
    }
}
