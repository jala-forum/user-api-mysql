package com.api.store.infra.database.mysql.repositories;

import com.api.store.model.entities.mysql.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MysqlSaleRepository extends JpaRepository<Sale, UUID> {
}
