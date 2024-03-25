package com.api.user.model.entities.mysql;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Setter
@Getter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String id;

    private String name;

    private int price;

    private int stock;
}
