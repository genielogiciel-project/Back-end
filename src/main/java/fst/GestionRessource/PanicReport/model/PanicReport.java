package fst.GestionRessource.PanicReport.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fst.GestionRessource.MaintenanceRecord.model.MaintenanceRecord;
import fst.GestionRessource.Resource.model.Resource;
import fst.GestionRessource.User.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PanicReport {
  @Id
  private String id;
  private String description;
  private LocalDate reportDate;

  @Enumerated(EnumType.STRING)
  private Status status;

  @ManyToOne
  @JoinColumn(name = "teacherId")
  private User teacher;

  @ManyToOne
  @JoinColumn(name = "resourceId")
  @JsonIgnoreProperties({"user", "department", "panicReports"})
  private Resource resource;

  @OneToOne(mappedBy = "panicReport")
  @JsonIgnoreProperties({"panicReport"})
  private MaintenanceRecord maintenanceRecord;
}
