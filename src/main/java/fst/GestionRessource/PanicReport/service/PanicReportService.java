package fst.GestionRessource.PanicReport.service;

import fst.GestionRessource.PanicReport.model.PanicReport;
import org.springframework.http.ResponseEntity;


public interface PanicReportService {
    ResponseEntity<?> getAllPanicReports();
    ResponseEntity<?> getPanicReportById(String id);
    ResponseEntity<?> addPanicReport(PanicReport panicReport);
    ResponseEntity<?> updatePanicReport(String id, PanicReport panicReport);
    ResponseEntity<?> deletePanicReport(String id);
}
