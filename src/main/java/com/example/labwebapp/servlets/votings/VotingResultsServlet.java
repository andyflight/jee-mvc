package com.example.labwebapp.servlets.votings;

import com.example.labwebapp.models.Candidate;
import com.example.labwebapp.models.Voting;
import com.example.labwebapp.services.VotingService;
import com.example.labwebapp.services.VotingServiceStubImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@WebServlet(name = "VotingResultsServlet", urlPatterns = "/results/*")
public class VotingResultsServlet extends VotingDetailServlet{
    private final VotingService votingService = new VotingServiceStubImpl();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
            String votingId = pathInfo.substring(1); // Extract the voting ID from the URL
            // Fetch voting details using the voting ID
            Voting voting = votingService.getVotingById(UUID.fromString(votingId)).orElse(null);
            if (voting != null) {
                Map<Candidate, Integer> results = votingService.getVotingResults(voting.getId());
                request.setAttribute("voting", voting);
                request.setAttribute("results", results);
                request.getRequestDispatcher("/WEB-INF/views/votingResults.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Voting not found");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid voting ID");
        }
    }
}
