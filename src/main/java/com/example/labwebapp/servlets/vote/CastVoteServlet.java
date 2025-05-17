package com.example.labwebapp.servlets.vote;

import com.example.labwebapp.models.User;
import com.example.labwebapp.models.Voting;
import com.example.labwebapp.services.VotingService;
import com.example.labwebapp.services.VotingServiceStubImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "CastVoteServlet", urlPatterns = "/vote")
public class CastVoteServlet extends HttpServlet {
    private final VotingService votingService = new VotingServiceStubImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String votingId = request.getParameter("votingId");
        Voting voting = votingService.getVotingById(UUID.fromString(votingId)).orElse(null);

        String candidateId = request.getParameter("candidateId");

        User user = (User) request.getSession().getAttribute("user");

        if (user == null || voting == null || candidateId == null) {
            response.sendRedirect(request.getContextPath() + "/votings");
            return;
        }

        if (voting.getCandidates().stream().noneMatch(c -> c.getId().equals(UUID.fromString(candidateId)))) {
            response.sendRedirect(request.getContextPath() + "/votings");
            return;
        }

        if (votingService.hasUserVoted(voting.getId(), user.getId())) {
            response.sendRedirect(request.getContextPath() + "/votings/" + voting.getId().toString());
            return;
        }

        votingService.vote(voting.getId(), user, UUID.fromString(candidateId));

        response.sendRedirect(request.getContextPath() + "/votings/" + voting.getId().toString());

    }

}
