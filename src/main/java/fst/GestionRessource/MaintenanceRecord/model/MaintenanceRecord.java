package fst.GestionRessource.MaintenanceRecord.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fst.GestionRessource.PanicReport.model.PanicReport;
import fst.GestionRessource.User.model.User;
import jakarta.persistence.Entity;
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
public class MaintenanceRecord {
  @Id
  private String id;
  private String details;
  private LocalDate maintenanceDate;

  @ManyToOne
  @JoinColumn(name = "technicianId")
  private User technician;

  @OneToOne
  @JoinColumn(name = "panicReportId", referencedColumnName = "id")
  @JsonIgnoreProperties({"maintenanceRecord"})
  private PanicReport panicReport;
}
