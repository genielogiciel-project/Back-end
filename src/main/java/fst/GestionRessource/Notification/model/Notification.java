package fst.GestionRessource.Notification.model;

import java.time.LocalDate;

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

  @Enumerated(EnumType.STRING)
  private Role role;

  @ManyToOne
  @JoinColumn(name = "departmentId")
  private Department department;

  @ManyToOne
  @JoinColumn(name = "userId")
  private User user;
}
