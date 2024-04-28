package com.api.store.service;

import com.api.store.infra.database.mongodb.repositories.MongoIdeaRepository;
import com.api.store.infra.database.mongodb.repositories.MongoTopicRepository;
import com.api.store.infra.database.mongodb.repositories.MongoUserRepository;
import com.api.store.infra.database.mongodb.repositories.MongoVoteRepository;
import com.api.store.model.entities.mongodb.Idea;
import com.api.store.model.entities.mongodb.Topic;
import com.api.store.model.entities.mongodb.User;
import com.api.store.model.entities.mongodb.Vote;
import com.api.store.utils.errors.ForbiddenError;
import com.api.store.utils.errors.GenericError;
import com.api.store.utils.errors.InvalidParamError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class IdeaService {
    private final MongoIdeaRepository ideaRepository;
    private final MongoTopicRepository topicRepository;
    private final MongoUserRepository userRepository;
    private final MongoVoteRepository voteRepository;


    @Autowired
    public IdeaService(MongoIdeaRepository ideaRepository, MongoTopicRepository topicRepository, MongoUserRepository userRepository, MongoVoteRepository voteRepository) {
        this.ideaRepository = ideaRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    }

    public void add(String text, String topicId, String userId) {
        Optional<Topic> topicOptional = this.topicRepository.findById(topicId);
        if (topicOptional.isEmpty()) throw new InvalidParamError("topicId");

        Optional<User> userOptional = this.userRepository.findById(userId);
        if (userOptional.isEmpty()) throw new InvalidParamError("userId");

        Topic topic = topicOptional.get();
        User user = userOptional.get();

        Idea idea = new Idea();
        idea.setText(text);
        idea.setTopicId(topic.getId());
        idea.setUserId(user.getId());

        this.ideaRepository.save(idea);
    }

    public Set<Idea> getIdeaByTopicId(String topicId) {
        return this.ideaRepository.findAllByTopicId(topicId);
    }

    public void deleteById(String ideaId, String userId) {
        Optional<Idea> optionalIdea = this.ideaRepository.findById(ideaId);
        if (optionalIdea.isEmpty()) throw new InvalidParamError("ideaId");

        Idea idea = optionalIdea.get();
        if (!idea.getUserId().equals(userId)) throw new ForbiddenError();

        this.ideaRepository.deleteById(ideaId);
    }

    public void addVote(String ideaId, String userId) {
        Optional<Idea> optionalIdea =  this.ideaRepository.findById(ideaId);
        if (optionalIdea.isEmpty()) throw new InvalidParamError("ideaId");

        Optional<User> optionalUser = this.userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new InvalidParamError("userId");

        Set<Vote> userVotes = this.voteRepository.findAllByUserId(optionalUser.get().getId());
        List<Vote> votes = userVotes.stream().filter((Vote a) -> a.getIdeaId().equals(ideaId)).toList();

        if ((long) votes.size() > 0) throw new GenericError("User has been already voted");

        Idea idea = optionalIdea.get();
        Vote vote = new Vote();
        vote.setIdeaId(idea.getId());
        vote.setUserId(optionalUser.get().getId());

        this.voteRepository.save(vote);
    }

    public void deleteVoteById(String voteId, String userId) {
        Optional<Vote> optionalVote = this.voteRepository.findById(voteId);
        if (optionalVote.isEmpty()) throw new InvalidParamError("voteId");

        Vote vote = optionalVote.get();
        if (!vote.getUserId().equals(userId)) throw new ForbiddenError();

        this.voteRepository.deleteById(voteId);
    }

    public List<Vote> getVoteByIdeaId(String ideaId) {
        return this.voteRepository.findAllByIdeaId(ideaId);
    }
}
