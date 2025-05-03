package fst.GestionRessource.Notification.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import fst.GestionRessource.Department.model.Department;
import fst.GestionRessource.User.model.Role;
import fst.GestionRessource.User.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
  @Id
  private String id;
  private String message;
  private LocalDate sentDate;
  private Boolean seen;

  @ManyToOne
  @JoinColumn(name = "senderId")
  @JsonIgnoreProperties({"sentNotifications", "notifications"})
  private User sender;

  @ManyToOne
  @JoinColumn(name = "receiverId")
  @JsonIgnoreProperties({"sentNotifications", "notifications"})
  private User receiver;
}
