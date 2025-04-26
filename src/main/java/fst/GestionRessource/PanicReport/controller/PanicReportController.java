package fst.GestionRessource.PanicReport.controller;

import fst.GestionRessource.PanicReport.model.PanicReport;
import fst.GestionRessource.PanicReport.service.PanicReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/panic-reports")
public class PanicReportController {

    private final PanicReportService panicReportService;

    public PanicReportController(PanicReportService panicReportService) {
        this.panicReportService = panicReportService;
    }

    @GetMapping
    public ResponseEntity<List<PanicReport>> getAllPanicReports() {
        return ResponseEntity.ok(panicReportService.getAllPanicReports());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PanicReport> getPanicReportById(@PathVariable String id) {
        return panicReportService.getPanicReportById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PanicReport> addPanicReport(@RequestBody PanicReport panicReport) {
        return ResponseEntity.ok(panicReportService.addPanicReport(panicReport));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PanicReport> updatePanicReport(@PathVariable String id, @RequestBody PanicReport panicReport) {
        return panicReportService.updatePanicReport(id, panicReport)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePanicReport(@PathVariable String id) {
        if (panicReportService.deletePanicReport(id)) {
            return ResponseEntity.ok("Panic report deleted successfully.");
        }
        return ResponseEntity.notFound().build();
    }
}