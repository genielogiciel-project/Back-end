package fst.GestionRessource.PanicReport.service;

import fst.GestionRessource.PanicReport.model.PanicReport;
import fst.GestionRessource.PanicReport.model.Status;
import fst.GestionRessource.PanicReport.repository.PanicReportRepository;
import fst.GestionRessource.Resource.model.Resource;
import fst.GestionRessource.Resource.model.ResourceStatus;
import fst.GestionRessource.Resource.repository.ResourceRepository;
import fst.GestionRessource.Utils.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PanicReportServiceImpl implements PanicReportService {
    private final PanicReportRepository panicReportRepository;
    private final ResourceRepository resourceRepository;

    @Override
    public ResponseEntity<?> getAllPanicReports() {
        return ResponseEntity.ok(panicReportRepository.findAll());
    }

    @Override
    public ResponseEntity<?> getPanicReportById(String id) {
        return ResponseEntity.ok(panicReportRepository.findById(id));
    }

    @Override
    public ResponseEntity<?> addPanicReport(PanicReport panicReport) {
        var ID = IdGenerator.generateId("PAN-");
        while (panicReportRepository.existsById(ID)) {
            ID = IdGenerator.generateId("PAN-");
        }
        panicReport.setId(ID);
        panicReport.setStatus(Status.OPEN);
        panicReport.setReportDate(LocalDate.now());
        panicReportRepository.save(panicReport);
        
        var resource = resourceRepository.findById(panicReport.getResource().getId()).orElse(null);
	      assert resource != null;
	      resource.setStatus(ResourceStatus.MAINTENANCE);
        resourceRepository.save(resource);
        
        return ResponseEntity.status(HttpStatus.CREATED).body("Panic created successfully");
    }

    @Override
    public ResponseEntity<?> updatePanicReport(String id, PanicReport panicReport) {
        if (panicReportRepository.existsById(id)) {
            panicReport.setId(id);
            panicReportRepository.save(panicReport);
            
            return ResponseEntity.ok("Panic updated successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Panic not found");
    }

    @Override
    public ResponseEntity<?> deletePanicReport(String id) {
        if (panicReportRepository.existsById(id)) {
            panicReportRepository.deleteById(id);
            return ResponseEntity.ok("Panic deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Panic not found");
    }
}