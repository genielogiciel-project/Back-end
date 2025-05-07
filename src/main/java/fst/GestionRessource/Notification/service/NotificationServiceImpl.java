package fst.GestionRessource.Notification.service;

import fst.GestionRessource.Notification.model.Notification;
import fst.GestionRessource.Notification.model.SendMessagesRequest;
import fst.GestionRessource.Notification.repository.NotificationRepository;
import fst.GestionRessource.User.repository.UserRepository;
import fst.GestionRessource.Utils.IdGenerator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository UserRepository;
  private final UserRepository userRepository;
  
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
    public ResponseEntity<?> getNotificationsByUser(String userId) {
      List<Notification> notifications = notificationRepository.findByReceiverId(userId);
      return ResponseEntity.ok(notifications);
    }

    @Override
    public ResponseEntity<?> addNotification(Notification notification) {
      notification.setSentDate(LocalDate.now());
      notification.setSeen(false);

      var ID = IdGenerator.generateId("NOTIF-");
      while (notificationRepository.existsById(ID)) {
        ID = IdGenerator.generateId("NOTIF-");
      }
      notification.setId(ID);

      if (notification.getReceiver() == null || notification.getSender() == null) {
        return ResponseEntity.status(400).body("Receiver or sender cannot be null");
      }

      Notification savedNotification = notificationRepository.save(notification);
      return ResponseEntity.ok(savedNotification);
    }
    
    @Override
    public ResponseEntity<?> sendMessage(SendMessagesRequest request) {
      System.out.println(request);
      var sender = userRepository.findById(request.getSender()).orElse(null);
      var message = request.getMessage();
      
      for (String receiverId : request.getReceivers()) {
        var receiver = userRepository.findById(receiverId).orElse(null);
        var notif = new Notification(null, message, null, null, request.getType(), sender, receiver);
        addNotification(notif);
      }
      
      return ResponseEntity.ok("The message has been sent successfully");
    }

    @Override
    public ResponseEntity<?> markNotificationAsRead(String id) {
        if (notificationRepository.existsById(id)) {
            Notification notification = notificationRepository.findById(id).get();
            notification.setSeen(true);
            Notification updatedNotification = notificationRepository.save(notification);
            return ResponseEntity.ok(updatedNotification);
        }
        return ResponseEntity.notFound().build();
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
