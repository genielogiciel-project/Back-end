package fst.GestionRessource.Department.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import fst.GestionRessource.Notification.model.Notification;
import fst.GestionRessource.Resource.model.Resource;
import fst.GestionRessource.ResourceRequest.model.ResourceRequest;
import fst.GestionRessource.User.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "resourceRequests"})
@ToString(exclude = {"users", "resources", "resourceRequests"})
public class Department {
  @Id
  private String id;
  private String name;

  @JsonIgnoreProperties({"department", "departmentHead", "callForTenders", "resources", "resourceRequests", "panicReports", "notifications", "maintenanceRecords"})
  @OneToOne
  @JoinColumn(name = "headId")
  private User head;

  @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnoreProperties({"department", "departmentHead", "resources", "resourceRequests"})
  private List<User> users;

  @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Resource> resources;

  @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ResourceRequest> resourceRequests;

  @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Notification> notifications;
}
