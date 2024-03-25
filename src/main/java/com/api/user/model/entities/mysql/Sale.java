package com.api.user.model.entities.mysql;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "sale")
public class Sale {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String id;


    private String ProductId;

    private String quantity;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
}
