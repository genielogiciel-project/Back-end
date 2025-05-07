package fst.GestionRessource.MaintenanceRecord.service;

import fst.GestionRessource.MaintenanceRecord.model.MaintenanceRecord;
import org.springframework.http.ResponseEntity;

public interface MaintenanceRecordService {
    ResponseEntity<?> getAllMaintenanceRecords();
    ResponseEntity<?> getMaintenanceRecordById(String id);
    ResponseEntity<?> addMaintenanceRecord(MaintenanceRecord maintenanceRecord);
    ResponseEntity<?> updateMaintenanceRecord(String id, MaintenanceRecord maintenanceRecord);
    ResponseEntity<?> deleteMaintenanceRecord(String id);
}