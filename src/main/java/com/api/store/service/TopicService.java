package com.api.store.service;

import com.api.store.infra.database.mongodb.repositories.MongoUserRepository;
import com.api.store.infra.database.mongodb.repositories.MongoTopicRepository;
import com.api.store.model.entities.mongodb.Topic;
import com.api.store.model.entities.mongodb.User;
import com.api.store.utils.errors.ForbiddenError;
import com.api.store.utils.errors.InvalidParamError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {
    private final MongoUserRepository userRepository;
    private final MongoTopicRepository topicRepository;

    @Autowired
    public TopicService(MongoTopicRepository topicRepository, MongoUserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }


    public void save(Topic topic, String userId) {
        Optional<User> user = this.userRepository.findById(userId);
        if (user.isEmpty()) throw new InvalidParamError("authorization");


        topic.setUserId(user.get().getId());
        this.topicRepository.save(topic);
    }

    public List<Topic> getTopic() {
        return this.topicRepository.findAll();
    }

    public void deleteById(String id, String userId) {
        Optional<Topic> topicOptional = this.topicRepository.findById(id);
        if (topicOptional.isEmpty()) throw new InvalidParamError("id");

        if (!topicOptional.get().getUserId().equals(userId)) throw new ForbiddenError();
        this.topicRepository.deleteById(id);
    }
}
