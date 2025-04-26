package fst.GestionRessource.MaintenanceRecord.controller;

import fst.GestionRessource.MaintenanceRecord.model.MaintenanceRecord;
import fst.GestionRessource.MaintenanceRecord.service.MaintenanceRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance-records")
public class MaintenanceRecordController {

    private final MaintenanceRecordService maintenanceRecordService;

    public MaintenanceRecordController(MaintenanceRecordService maintenanceRecordService) {
        this.maintenanceRecordService = maintenanceRecordService;
    }

    @GetMapping
    public ResponseEntity<List<MaintenanceRecord>> getAllMaintenanceRecords() {
        return ResponseEntity.ok(maintenanceRecordService.getAllMaintenanceRecords());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceRecord> getMaintenanceRecordById(@PathVariable String id) {
        return maintenanceRecordService.getMaintenanceRecordById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MaintenanceRecord> addMaintenanceRecord(@RequestBody MaintenanceRecord maintenanceRecord) {
        return ResponseEntity.ok(maintenanceRecordService.addMaintenanceRecord(maintenanceRecord));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceRecord> updateMaintenanceRecord(@PathVariable String id, @RequestBody MaintenanceRecord maintenanceRecord) {
        return maintenanceRecordService.updateMaintenanceRecord(id, maintenanceRecord)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMaintenanceRecord(@PathVariable String id) {
        if (maintenanceRecordService.deleteMaintenanceRecord(id)) {
            return ResponseEntity.ok("Maintenance record deleted successfully.");
        }
        return ResponseEntity.notFound().build();
    }
}