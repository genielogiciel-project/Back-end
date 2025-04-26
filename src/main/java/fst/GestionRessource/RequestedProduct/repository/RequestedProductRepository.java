package fst.GestionRessource.RequestedProduct.repository;

import fst.GestionRessource.RequestedProduct.model.RequestedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestedProductRepository extends JpaRepository<RequestedProduct, String> {
    // Custom query methods can be defined here if needed
}
