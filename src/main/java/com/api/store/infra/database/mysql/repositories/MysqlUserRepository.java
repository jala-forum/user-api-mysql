package com.api.store.infra.database.mysql.repositories;

import com.api.store.model.entities.mysql.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MysqlUserRepository extends JpaRepository<User, UUID> {

    @Query
    Optional<User> findByLogin(String login);
}