package fst.GestionRessource.MaintenanceRecord.repository;

import fst.GestionRessource.MaintenanceRecord.model.MaintenanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceRecordRepository extends JpaRepository<MaintenanceRecord, String> {
}