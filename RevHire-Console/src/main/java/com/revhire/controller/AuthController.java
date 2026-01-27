package com.revhire.controller;

import com.revhire.model.User;
import com.revhire.service.UserService;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;
    private final Scanner scanner;

    public AuthController(UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
    }

    public User start() {
        while (true) {
            System.out.println("\n=== RevHire: Welcome ===");
            System.out.println("1. Login");
            System.out.println("2. Register as Job Seeker");
            System.out.println("3. Register as Employer");
            System.out.println("4. Forgot Password");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    User user = login();
                    if (user != null)
                        return user;
                    break;
                case "2":
                    register("JOB_SEEKER");
                    break;
                case "3":
                    register("EMPLOYER");
                    break;
                case "4":
                    forgotPassword();
                    break;
                case "5":
                    logger.info("Goodbye!");
                    System.exit(0);
                    break;
                default:
                    logger.warn("Invalid option, please try again.");
            }
        }
    }

    private User login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        try {
            logger.info("Attempting login for user: {}", username);
            User user = userService.login(username, password);
            logger.info("Login successful! Welcome, {}", user.getName());
            return user;
        } catch (Exception e) {
            logger.warn("Login failed for user: {}. Reason: {}", username, e.getMessage());
            return null;
        }
    }

    private void register(String role) {
        System.out.println("\n=== Register as " + role + " ===");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Full Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        try {
            logger.info("Registering new user: {} with role: {}", username, role);
            User user = userService.registerUser(username, password, role, name, email);
            logger.info("Registration successful for user: {}", username);
            System.out.println("--- Setup Security Question (for password recovery) ---");
            System.out.print("Question: ");
            String q = scanner.nextLine();
            System.out.print("Answer: ");
            String a = scanner.nextLine();
            userService.setSecurityQuestion(user.getId(), q, a);
            userService.setSecurityQuestion(user.getId(), q, a);
            System.out.println("Security question saved. You can now login.");

        } catch (Exception e) {
            logger.error("Registration failed for user: {}", username, e);
        }
    }

    private void forgotPassword() {
        System.out.println("\n=== Reset Password ===");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        try {
            String question = userService.getSecurityQuestion(username);
            logger.info("Security Question: " + question);
            System.out.print("Answer: ");
            String answer = scanner.nextLine();

            if (userService.verifySecurityAnswer(username, answer)) {
                System.out.print("Enter new password: ");
                String newPass = scanner.nextLine();
                userService.resetPassword(username, newPass);
                logger.info("Password reset successfully! Please login.");
            } else {
                logger.warn("Incorrect answer.");
            }
        } catch (Exception e) {

            logger.error("Error during password reset for user: {}", username, e);
        }
    }
}
