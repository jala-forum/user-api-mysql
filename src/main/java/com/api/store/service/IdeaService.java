package com.api.store.service;

import com.api.store.infra.database.mysql.repositories.MysqlIdeaRepository;
import com.api.store.infra.database.mysql.repositories.MysqlTopicRepository;
import com.api.store.infra.database.mysql.repositories.MysqlUserRepository;
import com.api.store.infra.database.mysql.repositories.MysqlVoteRepository;
import com.api.store.model.entities.mysql.Idea;
import com.api.store.model.entities.mysql.Topic;
import com.api.store.model.entities.mysql.User;
import com.api.store.model.entities.mysql.Vote;
import com.api.store.utils.errors.ForbiddenError;
import com.api.store.utils.errors.GenericError;
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
    private final MysqlVoteRepository voteRepository;


    @Autowired
    public IdeaService(MysqlIdeaRepository ideaRepository, MysqlTopicRepository topicRepository, MysqlUserRepository userRepository, MysqlVoteRepository voteRepository) {
        this.ideaRepository = ideaRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
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

    public void deleteById(String ideaId, String userId) {
        Optional<Idea> optionalIdea = this.ideaRepository.findById(UUID.fromString(ideaId));
        if (optionalIdea.isEmpty()) throw new InvalidParamError("ideaId");

        Idea idea = optionalIdea.get();
        if (!idea.getUser().getId().toString().equals(userId)) throw new ForbiddenError();

        this.ideaRepository.deleteById(UUID.fromString(ideaId));
    }

    public void addVote(String ideaId, String userId) {
        Optional<Idea> optionalIdea =  this.ideaRepository.findById(UUID.fromString(ideaId));
        if (optionalIdea.isEmpty()) throw new InvalidParamError("ideaId");

        Optional<User> optionalUser = this.userRepository.findById(UUID.fromString(userId));
        if (optionalUser.isEmpty()) throw new InvalidParamError("userId");

        Set<Vote> userVotes = this.voteRepository.findAllByUser(optionalUser.get());
        List<Vote> votes = userVotes.stream().filter((Vote a) -> a.getIdea().getId().toString().equals(ideaId)).toList();

        if ((long) votes.size() > 0) throw new GenericError("User has been already voted");

        Idea idea = optionalIdea.get();
        Vote vote = new Vote();
        vote.setIdea(idea);
        vote.setUser(optionalUser.get());

        this.voteRepository.save(vote);
    }

    public void deleteVoteById(String voteId, String userId) {
        Optional<Vote> optionalVote = this.voteRepository.findById(UUID.fromString(voteId));
        if (optionalVote.isEmpty()) throw new InvalidParamError("voteId");

        Vote vote = optionalVote.get();
        if (!vote.getUser().getId().toString().equals(userId)) throw new ForbiddenError();

        this.voteRepository.deleteById(UUID.fromString(voteId));
    }

    public List<Vote> getVoteByIdeaId(String ideaId) {
        Optional<Idea> optionalIdea = this.ideaRepository.findById(UUID.fromString(ideaId));
        if (optionalIdea.isEmpty()) throw new InvalidParamError("ideaId");
        return optionalIdea.get().getVotes();
    }
}
