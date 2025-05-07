package fst.GestionRessource.MaintenanceRecord.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fst.GestionRessource.PanicReport.model.PanicReport;
import fst.GestionRessource.User.model.User;
import jakarta.persistence.*;
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
  @Enumerated(EnumType.STRING)
  private Severity severity;
  @Enumerated(EnumType.STRING)
  private Frequency frequency;
  @Enumerated(EnumType.STRING)
  private Origin origin;
  @Enumerated(EnumType.STRING)
  private MaintenanceStatus status;

  @ManyToOne
  @JoinColumn(name = "technicianId")
  private User technician;

  @OneToOne
  @JoinColumn(name = "panicReportId", referencedColumnName = "id")
  @JsonIgnoreProperties({"maintenanceRecord"})
  private PanicReport panicReport;
}
