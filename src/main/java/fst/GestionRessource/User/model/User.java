package fst.GestionRessource.User.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import fst.GestionRessource.CallForTender.model.CallForTender;
import fst.GestionRessource.Department.model.Department;
import fst.GestionRessource.MaintenanceRecord.model.MaintenanceRecord;
import fst.GestionRessource.Notification.model.Notification;
import fst.GestionRessource.PanicReport.model.PanicReport;
import fst.GestionRessource.Resource.model.Resource;
import fst.GestionRessource.ResourceRequest.model.ResourceRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails {
  @Id
  private String id;

  @Column(unique = true)
  private String userNumber;

  private String fullName;

  @JsonIgnore
  private String password;

  @Enumerated(EnumType.STRING)
  private List<Role> role;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "departmentId")
  private Department department;

  @JsonIgnoreProperties({"head", "user", "resources", "resourceRequests"})
  @OneToOne(mappedBy = "head", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Department departmentHead;

  @OneToMany(mappedBy = "resourceManager", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<CallForTender> callForTenders;

  @JsonIgnoreProperties({"user", "department", "panicReports"})
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Resource> resources;

  @JsonIgnoreProperties({"teacher", "department"})
  @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ResourceRequest> resourceRequests;

  @JsonIgnoreProperties({"teacher", "resource", "panicReport"})
  @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<PanicReport> panicReports;

  @JsonIgnoreProperties({"user"})
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Notification> notifications;

  @JsonIgnoreProperties({"technician", "panicReport"})
  @OneToMany(mappedBy = "technician", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<MaintenanceRecord> maintenanceRecords;

















  @JsonIgnore
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
      return role.stream()
              .map(role -> new SimpleGrantedAuthority(role.name()))
              .collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
      return password;
  }

  @JsonIgnore
  @Override
  public String getUsername() {
      return userNumber;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
      return true;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
      return true;
  }

  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
      return true;
  }

  @Override
  public String toString() {
      return "User{" +
              "id=" + id +
              ", userNumber='" + userNumber + '\'' +
              ", fullName='" + fullName + '\'' +
              ", password='" + password + '\'' +
              ", role=" + role +
              '}';
  }

  @JsonIgnore
  @Override
  public boolean isEnabled() {
      return true;
  }
}
