package com.revhire.service;

import com.revhire.dao.JobDAO;
import com.revhire.model.Job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JobService {
    private static final Logger logger = LoggerFactory.getLogger(JobService.class);
    private final JobDAO jobDAO;

    public JobService() {
        this(new JobDAO());
    }

    public JobService(JobDAO jobDAO) {
        this.jobDAO = jobDAO;
    }

    public Job postJob(int employerId, String title, String description, String requirements,
            String location, String salaryRange, String jobType, int experienceYears, String deadline)
            throws SQLException {
        logger.info("Attempting to post job: {} for employer: {}", title, employerId);
        Job job = new Job();
        job.setEmployerId(employerId);
        job.setTitle(title);
        job.setDescription(description);
        job.setRequirements(requirements);
        job.setLocation(location);
        job.setSalaryRange(salaryRange);
        job.setJobType(jobType);
        job.setExperienceYears(experienceYears);
        job.setDeadline(deadline);
        job.setStatus("OPEN");

        return jobDAO.createJob(job);
    }

    public boolean updateJob(int jobId, int employerId, String title, String description, String requirements,
            String location, String salaryRange, String jobType, int experienceYears, String deadline)
            throws SQLException {
        logger.info("Updating job: {}", jobId);
        Job job = new Job();
        job.setId(jobId);
        job.setEmployerId(employerId);
        job.setTitle(title);
        job.setDescription(description);
        job.setRequirements(requirements);
        job.setLocation(location);
        job.setSalaryRange(salaryRange);
        job.setJobType(jobType);
        job.setExperienceYears(experienceYears);
        job.setDeadline(deadline);

        return jobDAO.updateJob(job);
    }

    public boolean deleteJob(int jobId) throws SQLException {
        logger.info("Deleting job: {}", jobId);
        return jobDAO.deleteJob(jobId);
    }

    public boolean closeJob(int jobId) throws SQLException {
        return jobDAO.updateJobStatus(jobId, "CLOSED");
    }

    public List<Job> getJobsByEmployer(int employerId) {
        return jobDAO.getJobsByEmployerId(employerId);
    }

    public java.util.List<Job> searchJobs(String keyword) {
        return jobDAO.searchJobs(keyword);
    }

    public Optional<Job> getJobById(int id) {
        return jobDAO.getJobById(id);
    }

}
