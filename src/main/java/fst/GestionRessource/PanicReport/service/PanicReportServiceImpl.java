package fst.GestionRessource.PanicReport.service;

import fst.GestionRessource.PanicReport.model.PanicReport;
import fst.GestionRessource.PanicReport.repository.PanicReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PanicReportServiceImpl implements PanicReportService {

    private final PanicReportRepository panicReportRepository;

    public PanicReportServiceImpl(PanicReportRepository panicReportRepository) {
        this.panicReportRepository = panicReportRepository;
    }

    @Override
    public List<PanicReport> getAllPanicReports() {
        return panicReportRepository.findAll();
    }

    @Override
    public Optional<PanicReport> getPanicReportById(String id) {
        return panicReportRepository.findById(id);
    }

    @Override
    public PanicReport addPanicReport(PanicReport panicReport) {
        return panicReportRepository.save(panicReport);
    }

    @Override
    public Optional<PanicReport> updatePanicReport(String id, PanicReport panicReport) {
        if (panicReportRepository.existsById(id)) {
            panicReport.setId(id);
            return Optional.of(panicReportRepository.save(panicReport));
        }
        return Optional.empty();
    }

    @Override
    public boolean deletePanicReport(String id) {
        if (panicReportRepository.existsById(id)) {
            panicReportRepository.deleteById(id);
            return true;
        }
        return false;
    }
}