package com.api.store.controller;

import com.api.store.dto.topic.request.AddTopicRequestDto;
import com.api.store.model.entities.mysql.Topic;
import com.api.store.service.TopicService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topic")
public class TopicController {
    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping
    public void createTopic(@RequestBody @Valid AddTopicRequestDto data) {
        Topic topic = new Topic();
        BeanUtils.copyProperties(data, topic);
        this.topicService.save(topic);
    }
}
