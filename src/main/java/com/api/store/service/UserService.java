package com.api.store.service;

import com.api.store.infra.database.mysql.repositories.MysqlProductRepository;
import com.api.store.model.entities.mysql.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final MysqlProductRepository mysqlProductRepository;

    @Autowired
    public UserService(MysqlProductRepository mysqlProductRepository) {
        this.mysqlProductRepository = mysqlProductRepository;
    }

    public void save(User product) {
        this.mysqlProductRepository.save(product);
    }

    public List<User> getAll() {
        return this.mysqlProductRepository.findAll();
    }

    public User getById(String id) {
        Optional<User> optionalProduct = this.mysqlProductRepository.findById(UUID.fromString(id));

        return optionalProduct.orElse(null);
    }

    public void deleteById(String id) {
        this.mysqlProductRepository.deleteById(UUID.fromString(id));
    }

    public void editById(User product) {
        this.mysqlProductRepository.save(product);
    }
}
