package fst.GestionRessource.PanicReport.service;

import fst.GestionRessource.PanicReport.model.PanicReport;

import java.util.List;
import java.util.Optional;

public interface PanicReportService {
    List<PanicReport> getAllPanicReports();
    Optional<PanicReport> getPanicReportById(String id);
    PanicReport addPanicReport(PanicReport panicReport);
    Optional<PanicReport> updatePanicReport(String id, PanicReport panicReport);
    boolean deletePanicReport(String id);
}