package com.api.store.service;

import com.api.store.infra.database.mysql.repositories.MysqlTopicRepository;
import com.api.store.infra.database.mysql.repositories.MysqlUserRepository;
import com.api.store.model.entities.mysql.Topic;
import com.api.store.model.entities.mysql.User;
import com.api.store.utils.errors.InvalidParamError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TopicService {
    private final MysqlUserRepository userRepository;
    private final MysqlTopicRepository topicRepository;

    @Autowired
    public TopicService(MysqlTopicRepository topicRepository, MysqlUserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }


    public void save(Topic topic, String userId) {
        Optional<User> user = this.userRepository.findById(UUID.fromString(userId));
        if (user.isEmpty()) throw new InvalidParamError("authorization");


        topic.setUser(user.get());
        this.topicRepository.save(topic);
    }

    public List<Topic> getTopic() {
        return this.topicRepository.findAll();
    }
}
