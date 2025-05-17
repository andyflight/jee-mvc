package com.example.labwebapp.repositories;

import com.example.labwebapp.models.Vote;
import com.example.labwebapp.models.Voting;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class VotingRepositoryStubImpl implements VotingRepository {
    private static final VotingRepository instance = new VotingRepositoryStubImpl();
    private final Map<UUID, Voting> votings = new ConcurrentHashMap<>();

    private VotingRepositoryStubImpl() {
        // Default constructor
    }

    public static VotingRepository getInstance() {
        return instance;
    }

    @Override
    public List<Voting> findAll() {
        return new ArrayList<>(votings.values());
    }

    @Override
    public List<Voting> findByCreatorId(UUID id){
        return new ArrayList<>(votings.values()).stream().filter(voting -> voting.getCreator().getId().equals(id)).collect(Collectors.toList());
    }

    @Override
    public Optional<Voting> findById(UUID id) {
        return Optional.ofNullable(votings.get(id));
    }


    @Override
    public Voting save(Voting voting) {
        if (voting.getId() == null) {
            voting.setId(UUID.randomUUID());
        }
        
        votings.put(voting.getId(), voting);
        return voting;
    }

    @Override
    public void delete(UUID id) {
        Voting voting = votings.remove(id);

    }

    @Override
    public boolean hasUserVoted(UUID votingId, UUID userId) {
        Optional<Voting> votingOpt = findById(votingId);
        if (votingOpt.isPresent()) {
            Voting voting = votingOpt.get();
            return voting.getVotes().stream()
                    .anyMatch(vote -> vote.getUser().getId().equals(userId));
        }
        return false;
    }

    @Override
    public List<Vote> getVotesForVoting(UUID votingId) {
        Optional<Voting> votingOpt = findById(votingId);
        return votingOpt.map(Voting::getVotes).orElse(new ArrayList<>());
    }

    @Override
    public void updateVotingStatus(UUID votingId, boolean isActive) {
        Optional<Voting> votingOpt = findById(votingId);
        votingOpt.ifPresent(voting -> voting.setIsActive(isActive));
    }



}
