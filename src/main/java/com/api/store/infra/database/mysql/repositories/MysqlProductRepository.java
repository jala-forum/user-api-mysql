package com.api.store.infra.database.mysql.repositories;

import com.api.store.model.entities.mysql.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MysqlProductRepository extends JpaRepository<User, UUID> {
}