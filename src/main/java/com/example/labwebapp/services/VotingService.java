package com.example.labwebapp.services;

import com.example.labwebapp.models.Candidate;
import com.example.labwebapp.models.User;
import com.example.labwebapp.models.Vote;
import com.example.labwebapp.models.Voting;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VotingService {
    List<Voting> getAllVotings();
    List<Voting> getVotingsByCreatorId(UUID id);
    Optional<Voting> getVotingById(UUID id);
    Voting createVoting(String title, List<Candidate> candidates, User creator);
    void deleteVoting(UUID id);
    boolean hasUserVoted(UUID votingId, UUID userId);
    void vote(UUID votingId, User user, UUID candidateId);
    void startVoting(UUID votingId);
    void stopVoting(UUID votingId);
    Map<Candidate, Integer> getVotingResults(UUID votingId);
    String generateVotingLink(HttpServletRequest request, UUID votingId);
    String generateResultsLink(HttpServletRequest request, UUID votingId);
}
