package fst.GestionRessource.Notification.controller;

import fst.GestionRessource.Notification.model.Notification;
import fst.GestionRessource.Notification.model.SendMessagesRequest;
import fst.GestionRessource.Notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<?> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNotificationById(@PathVariable String id) {
      return notificationService.getNotificationById(id);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getNotificationsByUser(@PathVariable String id) {
        return notificationService.getNotificationsByUser(id);
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody SendMessagesRequest request) {
        return notificationService.sendMessage(request);
    }

    @PostMapping
    public ResponseEntity<?> addNotification(@RequestBody Notification notification) {
      return notificationService.addNotification(notification);
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<?> markNotificationAsRead(@PathVariable String id) {
        return notificationService.markNotificationAsRead(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNotification(@PathVariable String id, @RequestBody Notification notification) {
        return notificationService.updateNotification(id, notification);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable String id) {
        return notificationService.deleteNotification(id);
    }
}
