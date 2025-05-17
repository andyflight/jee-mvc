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
import java.util.List;

@WebServlet(name = "UserVotingsServlet", urlPatterns = "/votings/my")
public class UserVotingsServlet extends HttpServlet {
    private final VotingService votingService = new VotingServiceStubImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        List<Voting> votings = votingService.getVotingsByCreatorId(user.getId()); // Assuming userId is available in the session

        request.setAttribute("votings", votings);
        request.getRequestDispatcher("/WEB-INF/views/userVotings.jsp").forward(request, response);
    }
}
