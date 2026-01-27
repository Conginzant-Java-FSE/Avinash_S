package com.revhire.dao;

import com.revhire.model.Application;
import com.revhire.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationDAO {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationDAO.class);

    public Application createApplication(Application app) throws SQLException {
        String sql = "INSERT INTO Applications (job_id, seeker_id, resume_snapshot, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, app.getJobId());
            pstmt.setInt(2, app.getSeekerId());
            pstmt.setString(3, app.getResumeSnapshot());
            pstmt.setString(4, app.getStatus());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating application failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    app.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating application failed, no ID obtained.");
                }
            }
        }
        return app;
    }

    public List<Application> getApplicationsByJobId(int jobId) {
        List<Application> apps = new ArrayList<>();
        String sql = "SELECT * FROM Applications WHERE job_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, jobId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    apps.add(extractApplicationFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting applications by job ID: {}", jobId, e);
            e.printStackTrace();
        }
        return apps;
    }

    public List<Application> getApplicationsBySeekerId(int seekerId) {
        List<Application> apps = new ArrayList<>();
        String sql = "SELECT * FROM Applications WHERE seeker_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, seekerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    apps.add(extractApplicationFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting applications by seeker ID: {}", seekerId, e);
            e.printStackTrace();
        }
        return apps;
    }

    public void updateStatus(int applicationId, String status) throws SQLException {
        String sql = "UPDATE Applications SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, applicationId);
            pstmt.executeUpdate();
        }
    }

    public void withdrawApplication(int applicationId, String reason) throws SQLException {
        String sql = "UPDATE Applications SET status = 'WITHDRAWN', withdraw_reason = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, reason);
            pstmt.setInt(2, applicationId);
            pstmt.executeUpdate();
        }
    }

    private Application extractApplicationFromResultSet(ResultSet rs) throws SQLException {
        return new Application(
                rs.getInt("id"),
                rs.getInt("job_id"),
                rs.getInt("seeker_id"),
                rs.getString("resume_snapshot"),
                rs.getString("status"),
                rs.getString("withdraw_reason"),
                rs.getTimestamp("applied_at"));
    }
}
