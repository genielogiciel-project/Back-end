package fst.GestionRessource.PanicReport.repository;

import fst.GestionRessource.PanicReport.model.PanicReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PanicReportRepository extends JpaRepository<PanicReport, String> {
}