package com.api.store.service;

import com.api.store.infra.database.mongodb.repositories.MongoUserRepository;
import com.api.store.infra.database.mysql.repositories.MysqlUserRepository;
import com.api.store.model.entities.mysql.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MigrationService {
    private final MongoUserRepository mongoUserRepository;
    private final MysqlUserRepository mysqlUserRepository;

    @Autowired
    public MigrationService(MongoUserRepository mongoUserRepository, MysqlUserRepository mysqlUserRepository) {
        this.mongoUserRepository = mongoUserRepository;
        this.mysqlUserRepository = mysqlUserRepository;
    }

    public void migrateMysqlToMongodb() {
        List<com.api.store.model.entities.mysql.User> mysqlUsers = this.mysqlUserRepository.findAll();

        this.mongoUserRepository.saveAll(this.convertUser(mysqlUsers));
    }

    private List<com.api.store.model.entities.mongodb.User> convertUser(List<User> mysqlUsers) {
        List<com.api.store.model.entities.mongodb.User> mongodbUsers = new ArrayList<>();

        for (User userSql : mysqlUsers) {
            com.api.store.model.entities.mongodb.User userMongo = new com.api.store.model.entities.mongodb.User();
            BeanUtils.copyProperties(userSql, userMongo);

            mongodbUsers.add(userMongo);
        }

        return mongodbUsers;
    }
}
