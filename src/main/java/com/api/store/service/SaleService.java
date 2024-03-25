package com.api.store.service;

import com.api.store.infra.database.mysql.repositories.MysqlProductRepository;
import com.api.store.infra.database.mysql.repositories.MysqlSaleRepository;
import com.api.store.model.entities.mysql.Product;
import com.api.store.model.entities.mysql.Sale;
import com.api.store.utils.errors.DiscountCalculator;
import com.api.store.utils.errors.GenericError;
import com.api.store.utils.errors.InvalidParamError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Optional<Product> optionalProduct = this.productRepository.findById(UUID.fromString(productId));

        if (optionalProduct.isEmpty()) throw new InvalidParamError("productId");

        Product product = optionalProduct.get();
        if (product.getStock() - sale.getQuantity() < 0) throw new GenericError("No enough stock");

        sale.setProduct(product);
        sale.setTotalPrice(DiscountCalculator.calculate(product.getPrice() * sale.getQuantity(), sale.getQuantity()));
        this.saleRepository.save(sale);

        product.decreaseStock(sale.getQuantity());
        this.productRepository.save(product);
    }
    public List<Sale> get() {
        return this.saleRepository.findAll();
    }
}
