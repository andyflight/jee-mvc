package com.example.labwebapp.services;

import com.example.labwebapp.models.Candidate;
import com.example.labwebapp.models.User;
import com.example.labwebapp.models.Vote;
import com.example.labwebapp.models.Voting;
import com.example.labwebapp.repositories.VotingRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.servlet.http.HttpServletRequest;

import java.util.*;

@Stateless
public class VotingServiceStubImpl implements VotingService {

    @EJB
    private VotingRepository repository;

    @Override
    public List<Voting> getAllVotings() {
        return repository.findAll();
    }

    @Override
    public List<Voting> getVotingsByCreatorId(UUID id) {
        return repository.findByCreatorId(id);
    }

    @Override
    public Optional<Voting> getVotingById(UUID id) {
        return repository.findById(id);

    }

    @Override
    public Voting createVoting(String title, List<Candidate> candidates, User creator) {
        Voting voting = new Voting(UUID.randomUUID(), creator, new ArrayList<>(), candidates, title, Boolean.TRUE);
        return repository.save(voting);
    }

    @Override
    public void deleteVoting(UUID id) {
        repository.delete(id);
    }

    @Override
    public boolean hasUserVoted(UUID votingId, UUID userId) {
        return repository.hasUserVoted(votingId, userId);
    }

    @Override
    public void vote(UUID votingId, User user, UUID candidateId) {
        if (hasUserVoted(votingId, user.getId())) {
            throw new IllegalArgumentException("User has already voted");
        }
        Voting voting = repository.findById(votingId).orElseThrow(() -> new IllegalArgumentException("Voting not found"));
        Candidate candidate = voting.getCandidates().stream()
                .filter(c -> c.getId().equals(candidateId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Candidate not found"));
        Vote vote = new Vote(UUID.randomUUID(), user, candidate);
        voting.getVotes().add(vote);
        repository.save(voting);
    }

    @Override
    public void startVoting(UUID votingId) {
        Voting voting = repository.findById(votingId).orElseThrow(() -> new IllegalArgumentException("Voting not found"));
        voting.setIsActive(true);
        repository.save(voting);
    }

    @Override
    public void stopVoting(UUID votingId) {
        Voting voting = repository.findById(votingId).orElseThrow(() -> new IllegalArgumentException("Voting not found"));
        voting.setIsActive(false);
        repository.save(voting);
    }

    @Override
    public Map<Candidate, Integer> getVotingResults(UUID votingId) {
        Voting voting = repository.findById(votingId).orElseThrow(() -> new IllegalArgumentException("Voting not found"));
        Map<Candidate, Integer> results = new HashMap<>();
        voting.getVotes().forEach(
                vote -> results.put(
                        vote.getCandidate(),
                        results.getOrDefault(vote.getCandidate(), 0) + 1
                )
        );
        return results;
    }

    @Override
    public String generateVotingLink(HttpServletRequest request, UUID votingId) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/votings/" + votingId.toString();
    }

    @Override
    public String generateResultsLink(HttpServletRequest request, UUID votingId) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/results/" + votingId.toString();

    }
}
