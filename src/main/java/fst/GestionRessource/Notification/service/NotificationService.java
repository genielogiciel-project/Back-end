package fst.GestionRessource.Notification.service;

import fst.GestionRessource.Notification.model.Notification;
import org.springframework.http.ResponseEntity;

public interface NotificationService {
    public ResponseEntity<?> getAllNotifications();

    public ResponseEntity<?> getNotificationById(String id);

    public ResponseEntity<?> addNotification(Notification notification);

    public ResponseEntity<?> updateNotification(String id, Notification notification);

    public ResponseEntity<?> deleteNotification(String id);
}