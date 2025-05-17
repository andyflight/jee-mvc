package com.example.labwebapp.listeners;

import com.example.labwebapp.models.Candidate;
import com.example.labwebapp.models.User;
import com.example.labwebapp.services.UserService;
import com.example.labwebapp.services.UserServiceStubImpl;
import com.example.labwebapp.services.VotingService;
import com.example.labwebapp.services.VotingServiceStubImpl;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@WebListener
public class AppInitializer implements ServletContextListener {

    private final Logger log = LoggerFactory.getLogger(AppInitializer.class);

    @EJB
    private VotingService votingService;

    @EJB
    private UserService userService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Application is stating up...");

        initializeTestData();

        log.info("Application initialization completed successfully.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Application is shutting down...");

        log.info("Application shutdown completed.");
    }

    private void initializeTestData() {
        
        if (votingService.getAllVotings().isEmpty()) {
            log.info("Creating sample voting data...");

            User admin = userService.createUser("Admin");

            createSampleVoting1(votingService, admin);
            createSampleVoting2(votingService, admin);
            
            log.info("Sample data creation completed.");
        }
    }
    
    private void createSampleVoting1(VotingService votingService, User creator) {
        List<Candidate> candidates = new ArrayList<>();
        candidates.add(new Candidate(UUID.randomUUID(), "Option A"));
        candidates.add(new Candidate(UUID.randomUUID(), "Option B"));
        candidates.add(new Candidate(UUID.randomUUID(), "Option C"));
        
        votingService.createVoting("Sample Poll #1", candidates, creator);
    }
    
    private void createSampleVoting2(VotingService votingService, User creator) {
        List<Candidate> candidates = new ArrayList<>();
        candidates.add(new Candidate(UUID.randomUUID(), "Java"));
        candidates.add(new Candidate(UUID.randomUUID(), "Python"));
        candidates.add(new Candidate(UUID.randomUUID(), "JavaScript"));
        candidates.add(new Candidate(UUID.randomUUID(), "C#"));
        
        votingService.createVoting("Favorite Programming Language", candidates, creator);
    }
}
