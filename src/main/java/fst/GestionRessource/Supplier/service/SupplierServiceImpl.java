package fst.GestionRessource.Supplier.service;

import fst.GestionRessource.Supplier.model.Supplier;
import fst.GestionRessource.Supplier.repository.SupplierRepository;
import fst.GestionRessource.Utils.IdGenerator;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    // Assuming you have a SupplierRepository injected here
    @Autowired
    private final SupplierRepository supplierRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> getAllSuppliers() {
        return ResponseEntity.ok(supplierRepository.findAll());
    }

    @Override
    public ResponseEntity<?> getSupplierById(String id) {
        try {
            if (!supplierRepository.existsById(id)) {
                return ResponseEntity.status(404).body("Supplier not found");
            }
            return ResponseEntity.ok(supplierRepository.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> addSupplier(Supplier supplier) {
        try {
            var ID = IdGenerator.generateId("SUP-");
            while (supplierRepository.existsById(ID)) {
                ID = IdGenerator.generateId("SUP-");
            }
            supplier.setId(ID);
            supplier.setPassword(passwordEncoder.encode(supplier.getPassword()));
            supplierRepository.save(supplier);
            return ResponseEntity.ok("Supplier created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateSupplier(String id, Supplier supplier) {
        try {
            if (!supplierRepository.existsById(id)) {
                return ResponseEntity.status(404).body("Supplier not found");
            }
            Supplier existingSupplier = supplierRepository.findById(id).orElse(null);
            if (existingSupplier != null) {
              if (supplier.getFullName() != null && !supplier.getFullName().isEmpty()) {
                existingSupplier.setFullName(supplier.getFullName());
              }
              if (supplier.getUserNumber() != null && !supplier.getUserNumber().isEmpty()) {
                existingSupplier.setUserNumber(supplier.getUserNumber());
              }
              if (supplier.getPassword() != null && !supplier.getPassword().isEmpty()) {
                existingSupplier.setPassword(passwordEncoder.encode(supplier.getPassword()));
              }
                existingSupplier.setCompanyName(supplier.getCompanyName());
                existingSupplier.setAddress(supplier.getAddress());
                existingSupplier.setWebsite(supplier.getWebsite());
                existingSupplier.setBlacklistReason(supplier.getBlacklistReason());
                existingSupplier.setBlacklisted(supplier.isBlacklisted());
                existingSupplier.setManagerName(supplier.getManagerName());
                existingSupplier.setResources(supplier.getResources());
                // Update other fields as necessary
                supplierRepository.save(existingSupplier);
                // System.out.println(existingSupplier);
                return ResponseEntity.ok("Supplier updated successfully");
            }
            return ResponseEntity.ok("Unexpected error occurred, Supplier not found");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> deleteSupplier(String id) {
        try {
            if (supplierRepository.existsById(id)) {
                supplierRepository.deleteById(id);
                return ResponseEntity.ok("Supplier deleted successfully");
            }
            return ResponseEntity.status(404).body("Supplier not found");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }
}
