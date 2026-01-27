package com.revhire.dao;

import com.revhire.model.Job;
import com.revhire.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobDAO {

    public Job createJob(Job job) throws SQLException {
        String sql = "INSERT INTO Jobs (employer_id, title, description, requirements, location, salary_range, job_type, experience_years, deadline, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, job.getEmployerId());
            pstmt.setString(2, job.getTitle());
            pstmt.setString(3, job.getDescription());
            pstmt.setString(4, job.getRequirements());
            pstmt.setString(5, job.getLocation());
            pstmt.setString(6, job.getSalaryRange());
            pstmt.setString(7, job.getJobType());
            pstmt.setInt(8, job.getExperienceYears());
            try {
                if (job.getDeadline() != null && !job.getDeadline().isEmpty())
                    pstmt.setDate(9, java.sql.Date.valueOf(job.getDeadline()));
                else
                    pstmt.setDate(9, null);
            } catch (Exception e) {
                pstmt.setDate(9, null);
            }
            pstmt.setString(10, job.getStatus());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating job failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    job.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating job failed, no ID obtained.");
                }
            }
        }
        return job;
    }

    public List<Job> getJobsByEmployerId(int employerId) {
        List<Job> jobs = new ArrayList<>();
        String sql = "SELECT * FROM Jobs WHERE employer_id = ? AND status != 'DELETED'";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, employerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    jobs.add(extractJobFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobs;
    }

    public Optional<Job> getJobById(int id) {
        String sql = "SELECT * FROM Jobs WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(extractJobFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Job> searchJobs(String keyword) {
        List<Job> jobs = new ArrayList<>();
        String sql = "SELECT * FROM Jobs WHERE (LOWER(title) LIKE ? OR LOWER(location) LIKE ? OR LOWER(job_type) LIKE ?) AND status = 'OPEN'";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword.toLowerCase() + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    jobs.add(extractJobFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobs;
    }

    public boolean updateJob(Job job) throws SQLException {
        String query = "UPDATE jobs SET title = ?, description = ?, requirements = ?, location = ?, salary_range = ?, job_type = ?, experience_years = ?, deadline = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, job.getTitle());
            pstmt.setString(2, job.getDescription());
            pstmt.setString(3, job.getRequirements());
            pstmt.setString(4, job.getLocation());
            pstmt.setString(5, job.getSalaryRange());
            pstmt.setString(6, job.getJobType());
            pstmt.setInt(7, job.getExperienceYears());
            pstmt.setString(8, job.getDeadline());
            pstmt.setInt(9, job.getId());

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    public boolean updateJobStatus(int jobId, String status) throws SQLException {
        String query = "UPDATE jobs SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, jobId);

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    public boolean deleteJob(int jobId) throws SQLException {
        String query = "DELETE FROM jobs WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, jobId);

            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;
        }
    }

    private Job extractJobFromResultSet(ResultSet rs) throws SQLException {
        String deadlineStr = "";
        Date date = rs.getDate("deadline");
        if (date != null) {
            deadlineStr = date.toString();
        }

        return new Job(
                rs.getInt("id"),
                rs.getInt("employer_id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("requirements"),
                rs.getString("location"),
                rs.getString("salary_range"),
                rs.getString("job_type"),
                rs.getInt("experience_years"),
                deadlineStr,
                rs.getString("status"),
                rs.getTimestamp("posted_at"));
    }
}
