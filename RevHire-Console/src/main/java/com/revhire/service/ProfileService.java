package com.revhire.service;

import com.revhire.dao.ProfileDAO;
import com.revhire.model.Education;
import com.revhire.model.Experience;
import com.revhire.model.Project;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProfileService {
    private static final Logger logger = LoggerFactory.getLogger(ProfileService.class);
    private final ProfileDAO profileDAO;

    public ProfileService() {
        this.profileDAO = new ProfileDAO();
    }

    public void addEducation(Education edu) throws SQLException {
        logger.info("Adding education for user {}", edu.getUserId());
        profileDAO.addEducation(edu);
    }

    public List<Education> getEducation(int userId) {
        return profileDAO.getEducationByUserId(userId);
    }

    public void addExperience(Experience exp) throws SQLException {
        logger.info("Adding experience for user {}", exp.getUserId());
        profileDAO.addExperience(exp);
    }

    public List<Experience> getExperience(int userId) {
        return profileDAO.getExperienceByUserId(userId);
    }

    public void addProject(Project proj) throws SQLException {
        logger.info("Adding project for user {}", proj.getUserId());
        profileDAO.addProject(proj);
    }

    public List<Project> getProjects(int userId) {
        return profileDAO.getProjectsByUserId(userId);
    }

    public void displayUserProfile(int userId) {
        System.out.println("--- Education ---");
        List<Education> eduList = getEducation(userId);
        if (eduList.isEmpty())
            System.out.println("No education added.");
        else
            eduList.forEach(System.out::println);

        System.out.println("\n--- Experience ---");
        List<Experience> expList = getExperience(userId);
        if (expList.isEmpty())
            System.out.println("No experience added.");
        else
            expList.forEach(System.out::println);

        System.out.println("\n--- Projects ---");
        List<Project> projList = getProjects(userId);
        if (projList.isEmpty())
            System.out.println("No projects added.");
        else
            projList.forEach(System.out::println);
    }
}
