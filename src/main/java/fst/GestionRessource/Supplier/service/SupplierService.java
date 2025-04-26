package fst.GestionRessource.Supplier.service;

import fst.GestionRessource.Supplier.model.Supplier;
import org.springframework.http.ResponseEntity;

public interface SupplierService {

    public ResponseEntity<?> getAllSuppliers();

    public ResponseEntity<?> getSupplierById(String id);

    public ResponseEntity<?> addSupplier(Supplier supplier);

    public ResponseEntity<?> updateSupplier(String id, Supplier supplier);

    public ResponseEntity<?> deleteSupplier(String id);
}
