package com.example.labwebapp.servlets.votings;

import com.example.labwebapp.models.Voting;
import com.example.labwebapp.services.VotingService;
import com.example.labwebapp.services.VotingServiceStubImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "VotingListServlet", urlPatterns = {"/votings"})
public class VotingListServlet extends HttpServlet {
    private final VotingService votingService = new VotingServiceStubImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Voting> votings = votingService.getAllVotings();

        request.setAttribute("votings", votings);
        request.getRequestDispatcher("/WEB-INF/views/votingList.jsp").forward(request, response);
    }
}
