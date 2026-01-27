package com.revhire.dao;

import com.revhire.model.Education;
import com.revhire.model.Experience;
import com.revhire.model.Project;
import com.revhire.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProfileDAO {
    private static final Logger logger = LoggerFactory.getLogger(ProfileDAO.class);

    public void addEducation(Education edu) throws SQLException {
        String sql = "INSERT INTO Education (user_id, institution, degree, year, gpa) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, edu.getUserId());
            pstmt.setString(2, edu.getInstitution());
            pstmt.setString(3, edu.getDegree());
            pstmt.setInt(4, edu.getYear());
            pstmt.setDouble(5, edu.getGpa());
            pstmt.executeUpdate();
        }
    }

    public List<Education> getEducationByUserId(int userId) {
        List<Education> list = new ArrayList<>();
        String sql = "SELECT * FROM Education WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Education(
                            rs.getInt("id"), rs.getInt("user_id"),
                            rs.getString("institution"), rs.getString("degree"),
                            rs.getInt("year"), rs.getDouble("gpa")));
                }
            }
        } catch (SQLException e) {
            logger.error("Error retrieving education for user: {}", userId, e);
            e.printStackTrace();
        }
        return list;
    }

    public void addExperience(Experience exp) throws SQLException {
        String sql = "INSERT INTO Experience (user_id, company, role, start_date, end_date, description) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, exp.getUserId());
            pstmt.setString(2, exp.getCompany());
            pstmt.setString(3, exp.getRole());
            pstmt.setString(4, exp.getStartDate());
            pstmt.setString(5, exp.getEndDate());
            pstmt.setString(6, exp.getDescription());
            pstmt.executeUpdate();
        }
    }

    public List<Experience> getExperienceByUserId(int userId) {
        List<Experience> list = new ArrayList<>();
        String sql = "SELECT * FROM Experience WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Experience(
                            rs.getInt("id"), rs.getInt("user_id"),
                            rs.getString("company"), rs.getString("role"),
                            rs.getString("start_date"), rs.getString("end_date"),
                            rs.getString("description")));
                }
            }
        } catch (SQLException e) {
            logger.error("Error retrieving experience for user: {}", userId, e);
            e.printStackTrace();
        }
        return list;
    }

    public void addProject(Project proj) throws SQLException {
        String sql = "INSERT INTO Projects (user_id, title, description, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, proj.getUserId());
            pstmt.setString(2, proj.getTitle());
            pstmt.setString(3, proj.getDescription());
            pstmt.setString(4, proj.getRole());
            pstmt.executeUpdate();
        }
    }

    public List<Project> getProjectsByUserId(int userId) {
        List<Project> list = new ArrayList<>();
        String sql = "SELECT * FROM Projects WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Project(
                            rs.getInt("id"), rs.getInt("user_id"),
                            rs.getString("title"), rs.getString("description"),
                            rs.getString("role")));
                }
            }
        } catch (SQLException e) {
            logger.error("Error retrieving projects for user: {}", userId, e);
            e.printStackTrace();
        }
        return list;
    }
}
