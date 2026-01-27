package com.revhire.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBConnection {
    private static final Logger logger = LoggerFactory.getLogger(DBConnection.class);
    private static final String URL = "jdbc:mysql://localhost:3306/revhire_db?allowMultiQueries=true";
    private static final String USER = "root";
    private static final String PASSWORD = "1211";

    public static Connection getConnection() throws SQLException {
        logger.debug("Establishing database connection...");
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        logger.debug("Database connection established successfully.");
        return conn;
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {

            InputStream is = DBConnection.class.getResourceAsStream("/schema.sql");
            if (is == null) {
                throw new RuntimeException("Schema file not found!");
            }

            String sql = new BufferedReader(new InputStreamReader(is))
                    .lines().collect(Collectors.joining("\n"));

            stmt.execute(sql);
            logger.info("Database initialized successfully.");
        } catch (SQLException e) {
            logger.error("Failed to initialize database", e);
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize database", e);
        }
    }
}
