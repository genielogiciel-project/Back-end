package fst.GestionRessource.MaintenanceRecord.service;

import fst.GestionRessource.MaintenanceRecord.model.MaintenanceRecord;
import fst.GestionRessource.MaintenanceRecord.model.MaintenanceStatus;
import fst.GestionRessource.MaintenanceRecord.repository.MaintenanceRecordRepository;
import fst.GestionRessource.Notification.model.Notification;
import fst.GestionRessource.Notification.model.NotificationType;
import fst.GestionRessource.Notification.service.NotificationService;
import fst.GestionRessource.PanicReport.model.Status;
import fst.GestionRessource.PanicReport.repository.PanicReportRepository;
import fst.GestionRessource.Resource.model.ResourceStatus;
import fst.GestionRessource.Resource.repository.ResourceRepository;
import fst.GestionRessource.User.repository.UserRepository;
import fst.GestionRessource.Utils.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MaintenanceRecordServiceImpl implements MaintenanceRecordService {
    private final UserRepository userRepository;
    private final ResourceRepository resourceRepository;
    private final MaintenanceRecordRepository maintenanceRecordRepository;
    private final PanicReportRepository panicReportRepository;
    private final NotificationService notificationService;

    @Override
    public ResponseEntity<?> getAllMaintenanceRecords() {
        return ResponseEntity.ok(maintenanceRecordRepository.findAll());
    }

    @Override
    public ResponseEntity<?> getMaintenanceRecordById(String id) {
        var maintenanceRecord = maintenanceRecordRepository.findById(id);
        
        if (maintenanceRecord.isPresent()) {
            return ResponseEntity.ok(maintenanceRecord.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record not found");
    }

    @Override
    public ResponseEntity<?> addMaintenanceRecord(MaintenanceRecord maintenanceRecord) {
        var ID = IdGenerator.generateId("MR-");
        while (maintenanceRecordRepository.existsById(ID)) {
            ID = IdGenerator.generateId("MR-");
        }
        maintenanceRecord.setId(ID);
        var savedRecord = maintenanceRecordRepository.save(maintenanceRecord);
        
        if (savedRecord.getStatus().equals(MaintenanceStatus.RESOLVED)) {
            var panic = savedRecord.getPanicReport();
            var resource = panic.getResource();
            resource.setStatus(ResourceStatus.ASSIGNED);
            resourceRepository.save(resource);
            
            panic.setStatus(Status.RESOLVED);
            panicReportRepository.save(panic);
            
            var sender = savedRecord.getTechnician();
            var user = panic.getResource().getUser();
            var department = panic.getResource().getDepartment();
            
            if (user == null) {
                var users = userRepository.findAllByDepartment_Id(department.getId());
                var msg = "The resource " + panic.getResource().getInventoryNumber() + "has been resolved";
                users.forEach(receiver -> {
                    var notif = new Notification(null, msg, null, null, NotificationType.SUCCESS, sender, receiver);
                    notificationService.addNotification(notif);
                });
            } else {
	            var msg = "The resource " + panic.getResource().getInventoryNumber() + "has been resolved";
              var notif = new Notification(null, msg, null, null, NotificationType.SUCCESS, sender, user);
              notificationService.addNotification(notif);
                System.out.println(msg);
            }
        } else if (savedRecord.getStatus().equals(MaintenanceStatus.IN_PROGRESS)) {
            var panic = savedRecord.getPanicReport();
            panic.setStatus(Status.IN_PROGRESS);
            panicReportRepository.save(panic);
        }
        
        
        return ResponseEntity.ok("Record added successfully");
    }

    @Override
    public ResponseEntity<?> updateMaintenanceRecord(String id, MaintenanceRecord maintenanceRecord) {
        var existingMaintenanceRecord = maintenanceRecordRepository.findById(id);
        if (existingMaintenanceRecord.isPresent()) {
            if (maintenanceRecord.getDetails() != null) {
                existingMaintenanceRecord.get().setDetails(maintenanceRecord.getDetails());
            }
            if (maintenanceRecord.getOrigin() != null) {
                existingMaintenanceRecord.get().setOrigin(maintenanceRecord.getOrigin());
            }
            if (maintenanceRecord.getFrequency() != null) {
                existingMaintenanceRecord.get().setFrequency(maintenanceRecord.getFrequency());
            }
            if (maintenanceRecord.getStatus() != null) {
                existingMaintenanceRecord.get().setStatus(maintenanceRecord.getStatus());
            }
            if (maintenanceRecord.getSeverity() != null) {
                existingMaintenanceRecord.get().setSeverity(maintenanceRecord.getSeverity());
            }
            if (maintenanceRecord.getTechnician() != null) {
                existingMaintenanceRecord.get().setTechnician(maintenanceRecord.getTechnician());
            }
            if (maintenanceRecord.getPanicReport() != null) {
                existingMaintenanceRecord.get().setPanicReport(maintenanceRecord.getPanicReport());
            }
            
            maintenanceRecordRepository.save(existingMaintenanceRecord.get());
            return ResponseEntity.ok("Record successfully updated");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record not found");
    }

    @Override
    public ResponseEntity<?> deleteMaintenanceRecord(String id) {
        if (maintenanceRecordRepository.existsById(id)) {
            maintenanceRecordRepository.deleteById(id);
            return ResponseEntity.ok("Record successfully deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record not found");
    }
}