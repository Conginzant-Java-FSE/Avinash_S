package com.revhire.service;

import com.revhire.dao.ApplicationDAO;

import com.revhire.model.Application;
import com.revhire.model.Job;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationService {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationService.class);
    private final ApplicationDAO applicationDAO;
    private final JobService jobService;

    public ApplicationService() {
        this(new ApplicationDAO(), new JobService());
    }

    public ApplicationService(ApplicationDAO applicationDAO) {
        this.applicationDAO = applicationDAO;
        this.jobService = new JobService();
    }

    public ApplicationService(ApplicationDAO applicationDAO, JobService jobService) {
        this.applicationDAO = applicationDAO;
        this.jobService = jobService;
    }

    public Application applyForJob(int jobId, int seekerId, String resumeSnapshot) throws SQLException {
        logger.info("Seeker {} applying for job {}", seekerId, jobId);

        // Validate Job Exists
        if (!jobService.getJobById(jobId).isPresent()) {
            throw new SQLException("Job ID " + jobId + " not found.");
        }

        Application app = new Application();
        app.setJobId(jobId);
        app.setSeekerId(seekerId);
        app.setResumeSnapshot(resumeSnapshot);
        app.setStatus("APPLIED");

        return applicationDAO.createApplication(app);
    }

    public List<Application> getApplicationsForJob(int jobId) {
        return applicationDAO.getApplicationsByJobId(jobId);
    }

    public List<Application> getMyApplications(int seekerId) {
        return applicationDAO.getApplicationsBySeekerId(seekerId);
    }

    public void updateApplicationStatus(int applicationId, String status) throws SQLException {
        logger.info("Updating application {} status to {}", applicationId, status);
        applicationDAO.updateStatus(applicationId, status);
    }

    public void withdrawApplication(int applicationId, String reason) throws SQLException {
        logger.info("Withdrawing application {}. Reason: {}", applicationId, reason);
        applicationDAO.withdrawApplication(applicationId, reason);
    }
}
