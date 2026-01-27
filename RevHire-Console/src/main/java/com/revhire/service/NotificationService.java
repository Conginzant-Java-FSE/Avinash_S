package com.revhire.service;

import com.revhire.dao.NotificationDAO;
import com.revhire.model.Notification;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationDAO notificationDAO;

    public NotificationService() {
        this.notificationDAO = new NotificationDAO();
    }

    public void sendNotification(int userId, String message) {
        logger.info("Sending notification to user {}: {}", userId, message);
        notificationDAO.createNotification(userId, message);
    }

    public List<Notification> getMyNotifications(int userId) {
        return notificationDAO.getNotificationsForUser(userId);
    }

    public void markAsRead(int notificationId) {
        notificationDAO.markAsRead(notificationId);
    }
}
