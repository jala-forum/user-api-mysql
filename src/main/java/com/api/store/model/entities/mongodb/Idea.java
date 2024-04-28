package com.api.store.model.entities.mongodb;

import com.api.store.model.entities.mysql.Topic;
import com.api.store.model.entities.mysql.User;
import com.api.store.model.entities.mysql.Vote;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Document("idea")
public class Idea {
    @MongoId
    private UUID id;

    private String text;

    private List<Vote> votes;

    private User user;

    private Topic topic;
}
