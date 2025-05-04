package fst.GestionRessource.Notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fst.GestionRessource.Notification.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {
  List<Notification> findByReceiverId(String receiverId);
}
