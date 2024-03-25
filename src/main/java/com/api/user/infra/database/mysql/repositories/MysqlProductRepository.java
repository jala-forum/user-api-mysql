package com.api.user.infra.database.mysql.repositories;

import com.api.user.model.entities.mysql.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MysqlProductRepository extends JpaRepository<Product, UUID> {
}