package com.api.store.infra.database.mongodb.repositories;

import com.api.store.model.entities.mongodb.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MongoUserRepository extends MongoRepository<User, String> {
    @Query
    Optional<User> findByLogin(String login);
}
