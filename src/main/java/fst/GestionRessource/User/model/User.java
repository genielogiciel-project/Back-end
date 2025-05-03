package fst.GestionRessource.User.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fst.GestionRessource.Department.model.Department;
import jakarta.persistence.*;
import lombok.*;
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
@Table(name = "user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails {
  @Id
  private String id;

  @Column(unique = true)
  private String userNumber;

  private String fullName;

  @NonNull
  @JsonDeserialize(using = PasswordDeserializer.class)
  private String password;

  @Enumerated(EnumType.STRING)
  private List<Role> role;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "departmentId")
  @JsonIgnoreProperties({"users", "resources", "resourceRequests"})
  private Department department;

  @JsonIgnoreProperties({"head", "resources", "resourceRequests"})
  @OneToOne(mappedBy = "head", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Department departmentHead;

  @Override
  @JsonIgnore
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.stream()
            .map(role -> new SimpleGrantedAuthority(role.name()))
            .collect(Collectors.toList());
  }

  @Override
  @JsonIgnore
  public String getPassword() {
    return password;
  }

  @Override
  @JsonIgnore
  public String getUsername() {
    return userNumber;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isEnabled() {
    return true;
  }
}

