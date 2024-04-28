package com.api.store.model.entities.mongodb;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Setter
@Getter
@Document("user")
public class User {
    @MongoId
    private String id;

    private String name;

    private String login;

    private String password;
}
