package fst.GestionRessource.Supplier.repository;

import fst.GestionRessource.Supplier.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, String> {
    // For example, to find suppliers by name or other attributes
    List<Supplier> findByName(String name);
}
