package com.api.store.controller;

import com.api.store.dto.idea.request.AddIdeaRequestDto;
import com.api.store.service.IdeaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/idea")
public class IdeaController {
    private final IdeaService ideaService;

    @Autowired
    public IdeaController(IdeaService ideaService) {
        this.ideaService = ideaService;
    }

    @PostMapping
    public void addIdea(@RequestBody @Valid AddIdeaRequestDto data, HttpServletRequest request) {
        String text = data.text();
        String topicId = data.topicId();
        String userId = (String) request.getAttribute("userId");

        this.ideaService.add(text, topicId, userId);
    };
}
