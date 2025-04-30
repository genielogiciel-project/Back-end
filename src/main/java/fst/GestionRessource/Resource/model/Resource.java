package fst.GestionRessource.Resource.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fst.GestionRessource.Department.model.Department;
import fst.GestionRessource.PanicReport.model.PanicReport;
import fst.GestionRessource.Supplier.model.Supplier;
import fst.GestionRessource.User.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = { "user", "department" })
public class Resource {
  @Id
  private String id;
  @Column(unique = true)
  private String inventoryNumber;
  private String type;
  private String brand;
  private String specifications;
  @Enumerated(EnumType.STRING)
  private ResourceStatus status;
  private LocalDate acquisitionDate;
  private LocalDate warrantyEndDate;

  @ManyToOne
  @JoinColumn(name = "supplierId")
  private Supplier supplier;

  @ManyToOne
  @JoinColumn(name = "assignedToUser")
  private User user;

  @ManyToOne
  @JoinColumn(name = "assignedToDepartment")
  private Department department;

  @OneToMany(mappedBy = "resource")
  @JsonIgnoreProperties({"user", "department"})
  private List<PanicReport> panicReports;
}
