package com.api.store.model.entities.mongodb;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Setter
@Getter
@Document("vote")
public class Vote {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String id;

    private User user;
    private Idea idea;
}
