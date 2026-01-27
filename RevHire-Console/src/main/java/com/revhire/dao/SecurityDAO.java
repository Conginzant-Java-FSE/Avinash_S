package com.revhire.dao;

import com.revhire.util.DBConnection;
import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityDAO {
    private static final Logger logger = LoggerFactory.getLogger(SecurityDAO.class);

    public void setSecurityQuestion(int userId, String question, String answer) throws SQLException {
        String sql = "INSERT INTO Security_Questions (user_id, question, answer) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, question);
            pstmt.setString(3, answer);
            pstmt.executeUpdate();
        }
    }

    public boolean verifyAnswer(int userId, String answer) {
        String sql = "SELECT answer FROM Security_Questions WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("answer").equalsIgnoreCase(answer);
                }
            }
        } catch (SQLException e) {
            logger.error("Error verifying security answer for user: {}", userId, e);
            e.printStackTrace();
        }
        return false;
    }

    public String getQuestion(int userId) {
        String sql = "SELECT question FROM Security_Questions WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("question");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updatePassword(int userId, String newPassword) throws SQLException {
        String sql = "UPDATE Users SET password = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPassword);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
        }
    }
}
