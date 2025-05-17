package com.example.labwebapp.repositories;

import com.example.labwebapp.models.Candidate;
import com.example.labwebapp.models.User;
import com.example.labwebapp.models.Vote;
import com.example.labwebapp.models.Voting;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VotingRepository {
    List<Voting> findAll();
    List<Voting> findByCreatorId(UUID id);
    Optional<Voting> findById(UUID id);
    Voting save(Voting voting);
    void delete(UUID id);
    boolean hasUserVoted(UUID votingId, UUID userId);
    List<Vote> getVotesForVoting(UUID votingId);
    void updateVotingStatus(UUID votingId, boolean isActive);
}
