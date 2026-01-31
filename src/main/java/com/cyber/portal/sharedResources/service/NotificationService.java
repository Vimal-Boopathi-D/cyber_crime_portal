package com.cyber.portal.sharedResources.service;

public interface NotificationService {
    void sendNotification(String to, String subject, String message);
    void sendStatusUpdate(Long complaintId);
    void sendSMS(String phoneNumber, String message);
    void sendInAppNotification(Long userId, String message);
}
