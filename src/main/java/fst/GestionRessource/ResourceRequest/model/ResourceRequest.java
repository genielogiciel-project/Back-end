package fst.GestionRessource.ResourceRequest.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fst.GestionRessource.Department.model.Department;
import fst.GestionRessource.RequestedProduct.model.RequestedProduct;
import fst.GestionRessource.User.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResourceRequest {
  @Id
  private String id;
  @Enumerated(EnumType.STRING)
  private Status status;
  private LocalDate createdAt;

  @OneToMany(mappedBy = "resourceRequest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnoreProperties({"proposal", "callForTender"})
  private List<RequestedProduct> requestedProducts;

  @ManyToOne
  @JoinColumn(name = "teacherId")
  @JsonIgnoreProperties({"departmentHead"})
  private User teacher;

  @ManyToOne
  @JoinColumn(name = "departmentId")
  @JsonIgnoreProperties({"users", "resources", "resourceRequests"})
  private Department department;
}
