package fst.GestionRessource.ProposalProduct.service;

import fst.GestionRessource.ProposalProduct.model.ProposalProduct;
import org.springframework.http.ResponseEntity;

public interface ProposalProductService {
    public ResponseEntity<?> getAllProposalProducts();

    public ResponseEntity<?> getProposalProductById(String id);

    public ResponseEntity<?> addProposalProduct(ProposalProduct proposalProduct);

    public ResponseEntity<?> updateProposalProduct(String id, ProposalProduct proposalProduct);

    public ResponseEntity<?> deleteProposalProduct(String id);
}
