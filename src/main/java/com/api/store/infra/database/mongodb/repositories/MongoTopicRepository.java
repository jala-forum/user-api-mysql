package com.api.store.infra.database.mongodb.repositories;

import com.api.store.model.entities.mongodb.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoTopicRepository extends MongoRepository<Topic, String> { }
