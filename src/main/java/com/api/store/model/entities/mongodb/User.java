package com.api.store.model.entities.mongodb;

import com.api.store.model.entities.mysql.Idea;
import com.api.store.model.entities.mysql.Topic;
import com.api.store.model.entities.mysql.Vote;
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
@Document("user")
public class User {
    @MongoId
    private String id;

    private String name;

    private String login;

    private String password;

    private Set<Topic> topics;

    private Set<Idea> ideas;

    private Set<Vote> votes;
}
