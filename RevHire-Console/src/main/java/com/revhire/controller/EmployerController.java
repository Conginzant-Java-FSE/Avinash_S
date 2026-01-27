package com.revhire.controller;

import com.revhire.model.Application;
import com.revhire.model.Job;
import com.revhire.model.User;
import com.revhire.service.ApplicationService;
import com.revhire.service.JobService;
import com.revhire.service.NotificationService;

import com.revhire.service.UserService;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmployerController {
    private static final Logger logger = LoggerFactory.getLogger(EmployerController.class);
    private final User user;
    private final JobService jobService;
    private final ApplicationService appService;
    private final NotificationService notificationService;
    private final com.revhire.service.ProfileService profileService;
    private final UserService userService;
    private final Scanner scanner;

    public EmployerController(User user, JobService jobService, ApplicationService appService,
            NotificationService notificationService, com.revhire.service.ProfileService profileService,
            UserService userService, Scanner scanner) {
        this.user = user;
        this.jobService = jobService;
        this.appService = appService;
        this.notificationService = notificationService;
        this.profileService = profileService;
        this.userService = userService;
        this.scanner = scanner;
    }

    public void menu() {
        while (true) {
            System.out.println("\n=== Employer Menu: " + user.getName() + " ===");
            System.out.println("1. Post a Job (Enhanced)");
            System.out.println("2. View My Jobs");
            System.out.println("3. View Applications for a Job");
            System.out.println("4. Manage Jobs");
            System.out.println("5. Change Password");
            System.out.println("6. Logout");
            System.out.println("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    postJob();
                    break;
                case "2":
                    viewMyJobs();
                    break;
                case "3":
                    viewApplications();
                    break;
                case "4":
                    manageJobs();
                    break;
                case "5":
                    changePassword();
                    break;
                case "6":
                    return;
                default:
                    logger.info("Invalid option.");
            }
        }
    }

    private void postJob() {
        System.out.println("\n--- Post a Job ---");
        System.out.println("Title: ");
        String title = scanner.nextLine();
        System.out.println("Description: ");
        String description = scanner.nextLine();
        System.out.println("Requirements: ");
        String requirements = scanner.nextLine();
        System.out.println("Location: ");
        String location = scanner.nextLine();
        System.out.println("Salary Range: ");
        String salary = scanner.nextLine();

        System.out.println("Job Type (Full-time/Part-time): ");
        String type = scanner.nextLine();
        System.out.println("Experience Years (number): ");
        int exp = 0;
        try {
            exp = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
        }
        System.out.println("Deadline (YYYY-MM-DD): ");
        String deadline = scanner.nextLine();

        try {
            logger.info("Employer {} posting a new job: {}", user.getId(), title);
            jobService.postJob(user.getId(), title, description, requirements, location, salary, type, exp, deadline);
            System.out.println("Job posted successfully!");
        } catch (Exception e) {
            logger.error("Error posting job for employer: {}", user.getId(), e);
            logger.info("Error posting job: " + e.getMessage());
        }
    }

    private void viewMyJobs() {
        System.out.println("\n--- My Jobs ---");
        List<Job> jobs = jobService.getJobsByEmployer(user.getId());
        if (jobs.isEmpty()) {
            System.out.println("No jobs posted yet.");
        } else {
            jobs.forEach(System.out::println);
        }
    }

    private void viewApplications() {
        System.out.println("Enter Job ID to view applications: ");
        try {
            int jobId = Integer.parseInt(scanner.nextLine());
            List<Application> apps = appService.getApplicationsForJob(jobId);
            if (apps.isEmpty()) {
                System.out.println("No applications found for this job.");
            } else {
                for (Application app : apps) {
                    logger.info("App ID: " + app.getId() + " | Seeker ID: " + app.getSeekerId() + " | Status: "
                            + app.getStatus());
                    if (app.getStatus().equals("WITHDRAWN")) {
                        logger.info("  (Withdrawn: " + app.getWithdrawReason() + ")");
                    }
                    logger.info("  Resume Snapshot: " + app.getResumeSnapshot());
                }
                processApplications(apps);
            }
        } catch (NumberFormatException e) {
            logger.warn("Invalid job ID format entered by employer: {}", user.getId());
        }
    }

    private void processApplications(List<Application> apps) {
        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Update Application Status");
            System.out.println("2. View Applicant Profile");
            System.out.println("3. Back");
            System.out.println("Choose option: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                try {
                    System.out.println("Enter Application ID: ");
                    int appId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter new status (SHORTLISTED/REJECTED): ");
                    String status = scanner.nextLine().toUpperCase();

                    appService.updateApplicationStatus(appId, status);
                    logger.info("Status updated.");

                    Application targetApp = apps.stream().filter(a -> a.getId() == appId).findFirst().orElse(null);
                    if (targetApp != null) {
                        notificationService.sendNotification(targetApp.getSeekerId(),
                                "Your application for Job (ID: " + targetApp.getJobId() + ") was " + status);
                    }
                } catch (Exception e) {
                    logger.error("Error updating application status.", e);
                    logger.info("Error: " + e.getMessage());
                }
            } else if (choice.equals("2")) {
                System.out.println("Enter Applicant User ID (Seeker ID from list): ");
                try {
                    int seekerId = Integer.parseInt(scanner.nextLine());
                    viewApplicantProfile(seekerId);
                } catch (NumberFormatException e) {
                    logger.info("Invalid ID.");
                }
            } else if (choice.equals("3")) {
                break;
            } else {
                logger.info("Invalid option.");
            }
        }
    }

    private void viewApplicantProfile(int seekerId) {
        System.out.println("\n--- Applicant Profile ---");
        try {
            profileService.displayUserProfile(seekerId);
        } catch (Exception e) {
            logger.info("Could not load profile: " + e.getMessage());
        }
    }

    private void manageJobs() {
        System.out.println("\n--- Manage Jobs ---");
        List<Job> jobs = jobService.getJobsByEmployer(user.getId());
        if (jobs.isEmpty()) {
            System.out.println("No jobs to manage.");
            return;
        }
        jobs.forEach(System.out::println);

        System.out.println("Enter Job ID to manage (or 0 to cancel): ");
        try {
            int jobId = Integer.parseInt(scanner.nextLine());
            if (jobId == 0)
                return;

            Job selectedJob = jobs.stream().filter(j -> j.getId() == jobId).findFirst().orElse(null);
            if (selectedJob == null) {
                logger.info("Invalid Job ID.");
                return;
            }

            System.out.println("1. Edit Job");
            System.out.println("2. Delete/Close Job");
            System.out.println("Choose action: ");
            String action = scanner.nextLine();

            if (action.equals("1")) {
                editJob(selectedJob);
            } else if (action.equals("2")) {
                deleteJob(jobId);
            } else {
                logger.info("Invalid action.");
            }
        } catch (NumberFormatException e) {
            logger.info("Invalid input.");
        }
    }

    private void editJob(Job job) {
        logger.info("Editing Job: " + job.getTitle());
        System.out.println("Press Enter to keep current value.");

        logger.info("Title [" + job.getTitle() + "]: ");
        String title = scanner.nextLine();
        if (!title.isEmpty())
            job.setTitle(title);

        logger.info("Description [" + job.getDescription() + "]: ");
        String desc = scanner.nextLine();
        if (!desc.isEmpty())
            job.setDescription(desc);

        logger.info("Requirements [" + job.getRequirements() + "]: ");
        String req = scanner.nextLine();
        if (!req.isEmpty())
            job.setRequirements(req);

        logger.info("Location [" + job.getLocation() + "]: ");
        String loc = scanner.nextLine();
        if (!loc.isEmpty())
            job.setLocation(loc);

        logger.info("Salary [" + job.getSalaryRange() + "]: ");
        String salary = scanner.nextLine();
        if (!salary.isEmpty())
            job.setSalaryRange(salary);

        logger.info("Job Type (Full-time/Part-time) [" + job.getJobType() + "]: ");
        String type = scanner.nextLine();
        if (!type.isEmpty())
            job.setJobType(type);

        logger.info("Experience Years [" + job.getExperienceYears() + "]: ");
        String expStr = scanner.nextLine();
        if (!expStr.isEmpty()) {
            try {
                job.setExperienceYears(Integer.parseInt(expStr));
            } catch (Exception e) {
            }
        }

        logger.info("Deadline (YYYY-MM-DD) [" + job.getDeadline() + "]: ");
        String deadline = scanner.nextLine();
        if (!deadline.isEmpty())
            job.setDeadline(deadline);

        try {
            jobService.updateJob(job.getId(), job.getEmployerId(), job.getTitle(), job.getDescription(),
                    job.getRequirements(), job.getLocation(), job.getSalaryRange(),
                    job.getJobType(), job.getExperienceYears(), job.getDeadline());
            logger.info("Job updated successfully.");
        } catch (Exception e) {
            logger.error("Error updating job: {}", job.getId(), e);
            logger.info("Error updating job: " + e.getMessage());
        }
    }

    private void deleteJob(int jobId) {
        System.out.println("1. Close Job (Stop accepting new applications, keep record)");
        System.out.println("2. Permanently Delete Job");
        System.out.println("3. Cancel");
        System.out.println("Choose option: ");
        String choice = scanner.nextLine();

        try {
            if (choice.equals("1")) {
                jobService.closeJob(jobId);
                logger.info("Job closed.");
            } else if (choice.equals("2")) {
                System.out.println("Are you sure you want to PERMANENTLY delete this job? (yes/no): ");
                if (scanner.nextLine().equalsIgnoreCase("yes")) {
                    jobService.deleteJob(jobId);
                    System.out.println("Job deleted.");
                } else {
                    System.out.println("Cancelled.");
                }
            }
        } catch (Exception e) {
            logger.error("Error deleting/closing job: {}", jobId, e);
            logger.info("Error: " + e.getMessage());
        }
    }

    private void changePassword() {
        System.out.println("\n--- Change Password ---");
        System.out.println("Enter old password: ");
        String oldPass = scanner.nextLine();
        System.out.println("Enter new password: ");
        String newPass = scanner.nextLine();

        try {
            userService.changePassword(user, oldPass, newPass);
            logger.info("Password changed successfully.");
        } catch (Exception e) {
            logger.error("Error changing password for user: {}", user.getId(), e);
            logger.info("Error: " + e.getMessage());
        }
    }
}
