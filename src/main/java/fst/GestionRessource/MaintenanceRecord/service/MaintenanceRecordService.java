package fst.GestionRessource.MaintenanceRecord.service;

import fst.GestionRessource.MaintenanceRecord.model.MaintenanceRecord;

import java.util.List;
import java.util.Optional;

public interface MaintenanceRecordService {
    List<MaintenanceRecord> getAllMaintenanceRecords();
    Optional<MaintenanceRecord> getMaintenanceRecordById(String id);
    MaintenanceRecord addMaintenanceRecord(MaintenanceRecord maintenanceRecord);
    Optional<MaintenanceRecord> updateMaintenanceRecord(String id, MaintenanceRecord maintenanceRecord);
    boolean deleteMaintenanceRecord(String id);
}