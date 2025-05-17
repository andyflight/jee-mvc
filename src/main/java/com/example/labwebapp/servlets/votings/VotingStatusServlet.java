package com.example.labwebapp.servlets.votings;

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

@WebServlet(name = "VotingStatusServlet", urlPatterns = "/votings/status")
public class VotingStatusServlet extends HttpServlet {
    private final VotingService votingService = new VotingServiceStubImpl();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String votingId = request.getParameter("votingId");
        String status = request.getParameter("status");
        
        if (votingId == null || status == null) {
            response.sendRedirect(request.getContextPath() + "/votings");
            return;
        }
        
        User user = (User) request.getSession().getAttribute("user");
        Voting voting = votingService.getVotingById(UUID.fromString(votingId)).orElse(null);
        if (user == null || voting == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (!voting.getCreator().equals(user)) {
            response.sendRedirect(request.getContextPath() + "/votings");
            return;
        }

        if (status.equals("active")) {
            votingService.startVoting(voting.getId());
        } else if (status.equals("stop")) {
            votingService.stopVoting(voting.getId());
        } else {
            response.sendRedirect(request.getContextPath() + "/votings");
        }

        response.sendRedirect(request.getContextPath() + "/votings/my");
        
    }
}


