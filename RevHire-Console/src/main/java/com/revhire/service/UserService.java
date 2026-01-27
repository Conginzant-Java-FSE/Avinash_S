package com.revhire.service;

import com.revhire.dao.SecurityDAO;
import com.revhire.dao.UserDAO;
import com.revhire.model.User;
import com.revhire.util.PasswordUtil;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserDAO userDAO;
    private final SecurityDAO securityDAO;

    public UserService() {
        this.userDAO = new UserDAO();
        this.securityDAO = new SecurityDAO();
    }

    public User registerUser(String username, String password, String role, String name, String email)
            throws Exception {
        logger.info("Attempting to register user: {}", username);
        if (userDAO.getUserByUsername(username).isPresent()) {
            logger.warn("Registration failed - Username {} already exists", username);
            throw new Exception("Username already exists.");
        }
        if (userDAO.getUserByEmail(email).isPresent()) {
            logger.warn("Registration failed - Email {} already exists", email);
            throw new Exception("Email already exists.");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(PasswordUtil.hashPassword(password));
        newUser.setRole(role);
        newUser.setName(name);
        newUser.setEmail(email);

        return userDAO.createUser(newUser);
    }

    public User login(String username, String password) throws Exception {
        logger.info("User {} attempting to login", username);
        Optional<User> userOpt = userDAO.getUserByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (PasswordUtil.verifyPassword(password, user.getPassword())) {
                logger.info("User {} logged in successfully", username);
                return user;
            }
        }
        logger.warn("Failed login attempt for user {}", username);
        throw new Exception("Invalid username or password.");
    }

    public void setSecurityQuestion(int userId, String question, String answer) throws Exception {
        securityDAO.setSecurityQuestion(userId, question, answer);
    }

    public String getSecurityQuestion(String username) throws Exception {
        Optional<User> userOpt = userDAO.getUserByUsername(username);
        if (userOpt.isPresent()) {
            String q = securityDAO.getQuestion(userOpt.get().getId());
            if (q == null)
                throw new Exception("No security question set for this user.");
            return q;
        }
        throw new Exception("User not found.");
    }

    public boolean verifySecurityAnswer(String username, String answer) throws Exception {
        Optional<User> userOpt = userDAO.getUserByUsername(username);
        if (userOpt.isPresent()) {
            return securityDAO.verifyAnswer(userOpt.get().getId(), answer);
        }
        return false;
    }

    public void resetPassword(String username, String newPassword) throws Exception {
        Optional<User> userOpt = userDAO.getUserByUsername(username);
        if (userOpt.isPresent()) {
            securityDAO.updatePassword(userOpt.get().getId(), PasswordUtil.hashPassword(newPassword));
        }
    }

    public void changePassword(User user, String oldPassword, String newPassword) throws Exception {
        if (!PasswordUtil.verifyPassword(oldPassword, user.getPassword())) {
            throw new Exception("Incorrect old password.");
        }
        securityDAO.updatePassword(user.getId(), PasswordUtil.hashPassword(newPassword));
        user.setPassword(PasswordUtil.hashPassword(newPassword));
    }
}
