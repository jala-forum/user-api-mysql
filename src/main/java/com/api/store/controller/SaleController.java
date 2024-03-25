package com.api.store.controller;

import com.api.store.dto.sale.AddSaleDto;
import com.api.store.model.entities.mysql.Sale;
import com.api.store.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sale")
public class SaleController {
    private final SaleService saleService;

    @Autowired
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    void addSale(@RequestBody AddSaleDto data) {
        Sale sale = new Sale();
        sale.setQuantity(data.quantity);

        this.saleService.save(sale, data.productId);
    }

    @RequestMapping
    List<Sale> getSale() {
        return this.saleService.get();
    }
}
