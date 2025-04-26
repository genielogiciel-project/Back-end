package fst.GestionRessource.Supplier.controller;

import fst.GestionRessource.Supplier.model.Supplier;
import fst.GestionRessource.Supplier.service.SupplierServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {
    private SupplierServiceImpl service;

    @GetMapping
    public ResponseEntity<?> getAllSuppliers() {
        return service.getAllSuppliers();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getSupplierById(@PathVariable String id) {
        return service.getSupplierById(id);
    }
    @GetMapping("/add")
    public ResponseEntity<?> addSupplier(@RequestBody Supplier supplier) {
        return service.addSupplier(supplier);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSupplier(@PathVariable String id, @RequestBody Supplier supplier) {
        return service.updateSupplier(id, supplier);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable String id) {
        return service.deleteSupplier(id);
    }
}
