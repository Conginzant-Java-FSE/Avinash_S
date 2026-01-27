package com.revhire;

import com.revhire.controller.AuthController;
import com.revhire.controller.EmployerController;
import com.revhire.controller.JobSeekerController;
import com.revhire.model.User;
import com.revhire.service.ApplicationService;
import com.revhire.service.JobService;
import com.revhire.service.NotificationService;
import com.revhire.service.ProfileService;
import com.revhire.service.UserService;
import com.revhire.util.DBConnection;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DBConnection.initializeDatabase();

        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService();
        JobService jobService = new JobService();
        ApplicationService appService = new ApplicationService();
        ProfileService profileService = new ProfileService();
        NotificationService notificationService = new NotificationService();
        AuthController authController = new AuthController(userService, scanner);

        while (true) {
            User currentUser = authController.start();

            if (currentUser.getRole().equals("EMPLOYER")) {
                EmployerController employerController = new EmployerController(currentUser, jobService, appService,
                        notificationService, profileService, userService, scanner);
                employerController.menu();
            } else if (currentUser.getRole().equals("JOB_SEEKER")) {
                JobSeekerController jobSeekerController = new JobSeekerController(currentUser, jobService, appService,
                        profileService, notificationService, userService, scanner);
                jobSeekerController.menu();
            }
        }
    }
}
