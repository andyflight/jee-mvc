package com.example.labwebapp.servlets.votings;

import com.example.labwebapp.models.Candidate;
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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@WebServlet(name = "VotingCreateServlet", urlPatterns = "/votings/create")
public class VotingCreateServlet extends HttpServlet {
    private final VotingService votingService = new VotingServiceStubImpl();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String title = request.getParameter("title");
        List<String> candidatesNames = Arrays.stream(request.getParameterValues("candidates")).collect(Collectors.toList());
        List<Candidate> candidates = candidatesNames.stream().map(name -> new Candidate(UUID.randomUUID(), name)).collect(Collectors.toList());

        Voting voting = votingService.createVoting(title, candidates, user);

        response.sendRedirect(request.getContextPath() + "/votings/" + voting.getId());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/views/votingCreate.jsp").forward(request, response);
    }
}
