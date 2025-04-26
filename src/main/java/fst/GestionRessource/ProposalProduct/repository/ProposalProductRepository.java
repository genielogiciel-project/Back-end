package fst.GestionRessource.ProposalProduct.repository;

import fst.GestionRessource.ProposalProduct.model.ProposalProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProposalProductRepository extends JpaRepository<ProposalProduct, String> {
    // Custom query methods can be defined here if needed
}
