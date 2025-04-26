package fst.GestionRessource.MaintenanceRecord.service;

import fst.GestionRessource.MaintenanceRecord.model.MaintenanceRecord;
import fst.GestionRessource.MaintenanceRecord.repository.MaintenanceRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceRecordServiceImpl implements MaintenanceRecordService {

    private final MaintenanceRecordRepository maintenanceRecordRepository;

    public MaintenanceRecordServiceImpl(MaintenanceRecordRepository maintenanceRecordRepository) {
        this.maintenanceRecordRepository = maintenanceRecordRepository;
    }

    @Override
    public List<MaintenanceRecord> getAllMaintenanceRecords() {
        return maintenanceRecordRepository.findAll();
    }

    @Override
    public Optional<MaintenanceRecord> getMaintenanceRecordById(String id) {
        return maintenanceRecordRepository.findById(id);
    }

    @Override
    public MaintenanceRecord addMaintenanceRecord(MaintenanceRecord maintenanceRecord) {
        return maintenanceRecordRepository.save(maintenanceRecord);
    }

    @Override
    public Optional<MaintenanceRecord> updateMaintenanceRecord(String id, MaintenanceRecord maintenanceRecord) {
        if (maintenanceRecordRepository.existsById(id)) {
            maintenanceRecord.setId(id);
            return Optional.of(maintenanceRecordRepository.save(maintenanceRecord));
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteMaintenanceRecord(String id) {
        if (maintenanceRecordRepository.existsById(id)) {
            maintenanceRecordRepository.deleteById(id);
            return true;
        }
        return false;
    }
}