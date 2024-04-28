package com.api.store.model.entities.mongodb;

import com.api.store.model.entities.mysql.Vote;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Setter
@Getter
@Document("idea")
public class Idea {
    @MongoId
    private String id;

    private String text;

    private List<Vote> votes;

    private String userId;

    private String topicId;
}
