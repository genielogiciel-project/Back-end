package fst.GestionRessource.Notification.service;

import fst.GestionRessource.Notification.model.Notification;
import fst.GestionRessource.Notification.model.SendMessagesRequest;
import org.springframework.http.ResponseEntity;

public interface NotificationService {
    ResponseEntity<?> getAllNotifications();

    ResponseEntity<?> getNotificationById(String id);

    ResponseEntity<?> getNotificationsByUser(String userId);
    
    ResponseEntity<?> sendMessage(SendMessagesRequest request);

    ResponseEntity<?> addNotification(Notification notification);

    ResponseEntity<?> markNotificationAsRead(String id);

    ResponseEntity<?> updateNotification(String id, Notification notification);

    ResponseEntity<?> deleteNotification(String id);
}
