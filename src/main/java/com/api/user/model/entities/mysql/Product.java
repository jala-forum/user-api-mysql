package com.api.user.model.entities.mysql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String id;

    @OneToMany(mappedBy = "productId", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Sale> sales;

    private String name;

    private int price;

    private int stock;
}
