package fst.GestionRessource.ResourceRequest.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fst.GestionRessource.Department.model.Department;
import fst.GestionRessource.RequestedProduct.model.RequestedProduct;
import fst.GestionRessource.User.model.User;
import jakarta.persistence.Entity;
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
  private Status status;

  @OneToMany(mappedBy = "resourceRequest")
  @JsonIgnoreProperties({"proposal"})
  private List<RequestedProduct> requestedProducts;

  @ManyToOne
  @JoinColumn(name = "teacherId")
  private User teacher;

  @ManyToOne
  @JoinColumn(name = "departmentId")
  private Department department;
}
