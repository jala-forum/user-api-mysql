package com.api.store.service;

import com.api.store.infra.database.mysql.repositories.MysqlIdeaRepository;
import com.api.store.infra.database.mysql.repositories.MysqlTopicRepository;
import com.api.store.infra.database.mysql.repositories.MysqlUserRepository;
import com.api.store.model.entities.mysql.Idea;
import com.api.store.model.entities.mysql.Topic;
import com.api.store.model.entities.mysql.User;
import com.api.store.utils.errors.InvalidParamError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class IdeaService {
    private final MysqlIdeaRepository ideaRepository;
    private final MysqlTopicRepository topicRepository;
    private final MysqlUserRepository userRepository;


    @Autowired
    public IdeaService(MysqlIdeaRepository ideaRepository, MysqlTopicRepository topicRepository, MysqlUserRepository userRepository) {
        this.ideaRepository = ideaRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    public void add(String text, String topicId, String userId) {
        Optional<Topic> topicOptional = this.topicRepository.findById(UUID.fromString(topicId));
        if (topicOptional.isEmpty()) throw new InvalidParamError("topicId");

        Optional<User> userOptional = this.userRepository.findById(UUID.fromString(userId));
        if (userOptional.isEmpty()) throw new InvalidParamError("userId");

        Topic topic = topicOptional.get();
        User user = userOptional.get();

        Idea idea = new Idea();
        idea.setText(text);
        idea.setTopic(topic);
        idea.setUser(user);

        this.ideaRepository.save(idea);
    }

    public Set<Idea> getIdeaByTopicId(String topicId) {
        Optional<Topic> optionalTopic = this.topicRepository.findById(UUID.fromString(topicId));
        if (optionalTopic.isEmpty()) throw new InvalidParamError("topicId");

        Topic topic = optionalTopic.get();
        return topic.getIdeas();
    }
}
