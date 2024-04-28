package com.api.store.model.entities.mongodb;

import com.api.store.model.entities.mysql.Idea;
import com.api.store.model.entities.mysql.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@Document("topic")
public class Topic {
    @MongoId
    private String id;

    private User user;

    private String title;

    private String description;

    private Set<Idea> ideas;
}
