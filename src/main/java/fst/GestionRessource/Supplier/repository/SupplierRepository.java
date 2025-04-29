package fst.GestionRessource.Supplier.repository;

import fst.GestionRessource.Supplier.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SupplierRepository extends JpaRepository<Supplier, String> {
    // For example, to find suppliers by name or other attributes
    Supplier findByCompanyName(String name);
}
