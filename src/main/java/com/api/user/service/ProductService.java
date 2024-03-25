package com.api.user.service;

import com.api.user.infra.database.mysql.repositories.MysqlProductRepository;
import com.api.user.model.entities.mysql.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
