package com.api.store.service;

import com.api.store.infra.database.mysql.repositories.MysqlProductRepository;
import com.api.store.infra.database.mysql.repositories.MysqlSaleRepository;
import com.api.store.model.entities.mysql.Product;
import com.api.store.model.entities.mysql.Sale;
import com.api.store.utils.errors.InvalidParamError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SaleService {
    private final MysqlSaleRepository saleRepository;
    private final MysqlProductRepository productRepository;

    @Autowired
    public SaleService(MysqlSaleRepository saleRepository, MysqlProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
    }

    public void save(Sale sale, String productId) {
        Optional<Product> product = this.productRepository.findById(UUID.fromString(productId));

        if (product.isEmpty()) throw new InvalidParamError("productId");

        sale.setProduct(product.get());
        this.saleRepository.save(sale);
    }
}
