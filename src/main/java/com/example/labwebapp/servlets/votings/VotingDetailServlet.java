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
import java.util.UUID;

@WebServlet(name = "VotingDetailServlet", urlPatterns = {"/votings/*"})
public class VotingDetailServlet extends HttpServlet {
    private final VotingService votingService = new VotingServiceStubImpl();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
            String votingId = pathInfo.substring(1);
            Voting voting = votingService.getVotingById(UUID.fromString(votingId)).orElse(null);

            if (voting != null) {
                String votingUrl = votingService.generateVotingLink(request, voting.getId());
                String resultUrl = votingService.generateResultsLink(request, voting.getId());
                request.setAttribute("votingUrl", votingUrl);
                request.setAttribute("resultUrl", resultUrl);
                request.setAttribute("voting", voting);
                request.getRequestDispatcher("/WEB-INF/views/votingDetail.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Voting not found");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid voting ID");
        }
    }
}
