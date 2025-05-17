package com.example.labwebapp.models;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Voting {
    private UUID id;
    private User creator;
    private List<Vote> votes;
    private List<Candidate> candidates;
    private String title;
    private Boolean isActive;

    public Voting(){
        // Default constructor
    }

    public Voting(UUID id, User creator, List<Vote> votes, List<Candidate> candidates, String title, Boolean isActive) {
        this.id = id;
        this.creator = creator;
        this.votes = votes;
        this.candidates = candidates;
        this.title = title;
        this.isActive = isActive;
    }

    public UUID getId() {
        return id;
    }

    public User getCreator() {
        return creator;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Voting)) return false;

        Voting other = (Voting) o;

        // Порівнюємо всі поля напряму
        return Objects.equals(id, other.id)
                && Objects.equals(creator, other.creator)
                && Objects.equals(votes, other.votes)
                && Objects.equals(candidates, other.candidates)
                && Objects.equals(title, other.title)
                && Objects.equals(isActive, other.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                creator,
                votes,
                candidates,
                title,
                isActive
        );
    }

}
