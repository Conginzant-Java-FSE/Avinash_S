package com.revhire.controller;

import com.revhire.model.*;
import com.revhire.service.ApplicationService;
import com.revhire.service.JobService;
import com.revhire.service.NotificationService;
import com.revhire.service.ProfileService;
import com.revhire.service.UserService;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobSeekerController {
    private static final Logger logger = LoggerFactory.getLogger(JobSeekerController.class);
    private final User user;
    private final JobService jobService;
    private final ApplicationService appService;
    private final ProfileService profileService;
    private final NotificationService notificationService;
    private final UserService userService;
    private final Scanner scanner;

    public JobSeekerController(User user, JobService jobService, ApplicationService appService,
            ProfileService profileService, NotificationService notificationService, UserService userService,
            Scanner scanner) {
        this.user = user;
        this.jobService = jobService;
        this.appService = appService;
        this.profileService = profileService;
        this.notificationService = notificationService;
        this.userService = userService;
        this.scanner = scanner;
    }

    public void menu() {
        while (true) {
            System.out.println("\n=== Job Seeker Menu: " + user.getName() + " ===");
            System.out.println("1. Profile Management");
            System.out.println("2. Search Jobs");
            System.out.println("3. Apply for a Job");
            System.out.println("4. My Applications");
            System.out.println("5. Notifications");
            System.out.println("6. Change Password");
            System.out.println("7. Logout");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    profileMenu();
                    break;
                case "2":
                    searchJobs();
                    break;
                case "3":
                    applyForJob();
                    break;
                case "4":
                    viewMyApplications();
                    break;
                case "5":
                    viewNotifications();
                    break;
                case "6":
                    changePassword();
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void profileMenu() {
        while (true) {
            System.out.println("\n--- Profile Management ---");
            System.out.println("1. Add Education");
            System.out.println("2. Add Experience");
            System.out.println("3. Add Project");
            System.out.println("4. View My Full Profile");
            System.out.println("5. Back");
            System.out.print("Choose: ");
            String c = scanner.nextLine();

            try {
                if (c.equals("1")) {
                    System.out.print("Institution: ");
                    String inst = scanner.nextLine();
                    System.out.print("Degree: ");
                    String deg = scanner.nextLine();
                    System.out.print("Year: ");
                    int yr = Integer.parseInt(scanner.nextLine());
                    System.out.print("GPA: ");
                    double gpa = Double.parseDouble(scanner.nextLine());
                    profileService.addEducation(new Education(0, user.getId(), inst, deg, yr, gpa));
                    logger.info("Added education for user: {}", user.getId());
                } else if (c.equals("2")) {
                    System.out.print("Company: ");
                    String comp = scanner.nextLine();
                    System.out.print("Role: ");
                    String role = scanner.nextLine();
                    System.out.print("Start: ");
                    String start = scanner.nextLine();
                    System.out.print("End: ");
                    String end = scanner.nextLine();
                    System.out.print("Desc: ");
                    String desc = scanner.nextLine();
                    profileService.addExperience(new Experience(0, user.getId(), comp, role, start, end, desc));
                    logger.info("Added experience for user: {}", user.getId());
                } else if (c.equals("3")) {
                    System.out.print("Title: ");
                    String ti = scanner.nextLine();
                    System.out.print("Role: ");
                    String ro = scanner.nextLine();
                    System.out.print("Desc: ");
                    String de = scanner.nextLine();
                    profileService.addProject(new Project(0, user.getId(), ti, de, ro));
                    logger.info("Added project for user: {}", user.getId());
                } else if (c.equals("4")) {
                    System.out.println("USER: " + user.getName());
                    System.out.println("--- Education ---");
                    profileService.getEducation(user.getId()).forEach(System.out::println);
                    System.out.println("--- Experience ---");
                    profileService.getExperience(user.getId()).forEach(System.out::println);
                    System.out.println("--- Projects ---");
                    profileService.getProjects(user.getId()).forEach(System.out::println);
                } else if (c.equals("5"))
                    break;
            } catch (Exception e) {
                logger.error("Error in profile management for user: {}", user.getId(), e);
                System.out.println("Error input format.");
            }
        }
    }

    private void searchJobs() {
        System.out.print("Enter keyword (Title/Location/Type) or empty for all open jobs: ");
        String keyword = scanner.nextLine();
        List<Job> jobs = jobService.searchJobs(keyword);

        if (jobs.isEmpty()) {
            System.out.println("No open jobs found.");
        } else {
            jobs.forEach(System.out::println);
        }
    }

    private void applyForJob() {
        System.out.print("Enter Job ID to apply: ");
        try {
            int jobId = Integer.parseInt(scanner.nextLine());
            String resumeSnapshot = "Snapshot of Profile for " + user.getName() + " at " + new java.util.Date();

            appService.applyForJob(jobId, user.getId(), resumeSnapshot);
            logger.info("Applied successfully!");
            notificationService.sendNotification(user.getId(), "You applied for Job ID " + jobId);
        } catch (Exception e) {
            logger.error("Error applying for job. User: {}", user.getId(), e);
            System.out.println("Error applying for job: " + e.getMessage());
        }
    }

    private void viewMyApplications() {
        System.out.println("\n--- My Applications ---");
        List<Application> apps = appService.getMyApplications(user.getId());
        if (apps.isEmpty()) {
            System.out.println("No applications submitted.");
        } else {
            for (Application app : apps) {
                System.out.println(app);
            }
            System.out.println("Do you want to withdraw any application? (yes/no)");
            if (scanner.nextLine().equalsIgnoreCase("yes")) {
                System.out.print("Enter App ID: ");
                int appId = Integer.parseInt(scanner.nextLine());
                System.out.print("Reason: ");
                String reason = scanner.nextLine();
                try {
                    appService.withdrawApplication(appId, reason);
                    logger.info("Withdrawn.");
                } catch (Exception e) {
                    logger.error("Error withdrawing application. App ID: {}", appId, e);
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }

    private void viewNotifications() {
        System.out.println("\n--- Notifications ---");
        List<Notification> notifs = notificationService.getMyNotifications(user.getId());
        if (notifs.isEmpty())
            System.out.println("No notifications.");
        else {
            for (Notification n : notifs) {
                System.out.println(n);
                if (!n.isRead())
                    notificationService.markAsRead(n.getId());
            }
        }
    }

    private void changePassword() {
        System.out.println("\n--- Change Password ---");
        System.out.print("Enter old password: ");
        String oldPass = scanner.nextLine();
        System.out.print("Enter new password: ");
        String newPass = scanner.nextLine();

        try {
            userService.changePassword(user, oldPass, newPass);
            logger.info("Password changed successfully.");
        } catch (Exception e) {
            logger.error("Error changing password for user: {}", user.getId(), e);
            System.out.println("Error: " + e.getMessage());
        }
    }
}
