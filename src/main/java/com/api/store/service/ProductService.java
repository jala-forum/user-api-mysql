package com.api.store.service;

import com.api.store.infra.database.mysql.repositories.MysqlProductRepository;
import com.api.store.model.entities.mysql.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private final MysqlProductRepository mysqlProductRepository;

    @Autowired
    public ProductService(MysqlProductRepository mysqlProductRepository) {
        this.mysqlProductRepository = mysqlProductRepository;
    }

    public void save(Product product) {
        this.mysqlProductRepository.save(product);
    }

    public List<Product> getAll() {
        return this.mysqlProductRepository.findAll();
    }

    public Product getById(String id) {
        Optional<Product> optionalProduct = this.mysqlProductRepository.findById(UUID.fromString(id));

        return optionalProduct.orElse(null);

    }
}
