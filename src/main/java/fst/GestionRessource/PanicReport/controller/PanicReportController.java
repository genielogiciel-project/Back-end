package fst.GestionRessource.PanicReport.controller;

import fst.GestionRessource.PanicReport.model.PanicReport;
import fst.GestionRessource.PanicReport.service.PanicReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/panic-reports")
public class PanicReportController {
    private final PanicReportService panicReportService;

    @GetMapping
    public ResponseEntity<?> getAllPanicReports() {
        return panicReportService.getAllPanicReports();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPanicReportById(@PathVariable String id) {
        return panicReportService.getPanicReportById(id);
    }

    @PostMapping
    public ResponseEntity<?> addPanicReport(@RequestBody PanicReport panicReport) {
        return panicReportService.addPanicReport(panicReport);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePanicReport(@PathVariable String id, @RequestBody PanicReport panicReport) {
        return panicReportService.updatePanicReport(id, panicReport);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePanicReport(@PathVariable String id) {
        return panicReportService.deletePanicReport(id);
    }
}