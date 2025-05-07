package fst.GestionRessource.MaintenanceRecord.controller;

import fst.GestionRessource.MaintenanceRecord.model.MaintenanceRecord;
import fst.GestionRessource.MaintenanceRecord.service.MaintenanceRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/maintenance-records")
public class MaintenanceRecordController {
    private final MaintenanceRecordService maintenanceRecordService;
    
    @GetMapping
    public ResponseEntity<?> getAllMaintenanceRecords() {
        return maintenanceRecordService.getAllMaintenanceRecords();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMaintenanceRecordById(@PathVariable String id) {
        return maintenanceRecordService.getMaintenanceRecordById(id);
    }

    @PostMapping
    public ResponseEntity<?> addMaintenanceRecord(@RequestBody MaintenanceRecord maintenanceRecord) {
        return maintenanceRecordService.addMaintenanceRecord(maintenanceRecord);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMaintenanceRecord(@PathVariable String id, @RequestBody MaintenanceRecord maintenanceRecord) {
        return maintenanceRecordService.updateMaintenanceRecord(id, maintenanceRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMaintenanceRecord(@PathVariable String id) {
        return maintenanceRecordService.deleteMaintenanceRecord(id);
    }
}