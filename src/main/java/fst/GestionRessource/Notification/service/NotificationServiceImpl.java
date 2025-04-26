package fst.GestionRessource.Notification.service;

import fst.GestionRessource.Notification.model.Notification;
import fst.GestionRessource.Notification.repository.NotificationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public ResponseEntity<?> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        return ResponseEntity.ok(notifications);
    }

    @Override
    public ResponseEntity<?> getNotificationById(String id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        return notification.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<?> addNotification(Notification notification) {
        Notification savedNotification = notificationRepository.save(notification);
        return ResponseEntity.ok(savedNotification);
    }

    @Override
    public ResponseEntity<?> updateNotification(String id, Notification notification) {
        if (notificationRepository.existsById(id)) {
            notification.setId(id);
            Notification updatedNotification = notificationRepository.save(notification);
            return ResponseEntity.ok(updatedNotification);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<?> deleteNotification(String id) {
        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
            return ResponseEntity.ok("Notification deleted successfully.");
        }
        return ResponseEntity.notFound().build();
    }
}