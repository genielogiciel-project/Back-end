package fst.GestionRessource.Supplier.controller;

import fst.GestionRessource.Supplier.model.Supplier;
import fst.GestionRessource.Supplier.service.SupplierServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {
    private final SupplierServiceImpl service;

    public SupplierController(SupplierServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAllSuppliers() {
        return service.getAllSuppliers();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getSupplierById(@PathVariable String id) {
        return service.getSupplierById(id);
    }
    @PostMapping("/register")
    public ResponseEntity<?> addSupplier(@RequestBody Supplier supplier) {
        System.out.println("Received supplier: " + supplier.getPassword());  // Check if password is correctly set
        service.addSupplier(supplier);
        return ResponseEntity.status(HttpStatus.CREATED).body("Supplier created successfully");
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSupplier(@PathVariable String id, @RequestBody Supplier supplier) {
        return service.updateSupplier(id, supplier);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable String id) {
        return service.deleteSupplier(id);
    }
}
