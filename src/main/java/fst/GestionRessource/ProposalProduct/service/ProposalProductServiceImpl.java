package fst.GestionRessource.ProposalProduct.service;

import fst.GestionRessource.ProposalProduct.model.ProposalProduct;
import fst.GestionRessource.ProposalProduct.repository.ProposalProductRepository;
import fst.GestionRessource.Utils.IdGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProposalProductServiceImpl implements ProposalProductService{

    private final ProposalProductRepository proposalProductRepository;

    public ProposalProductServiceImpl(ProposalProductRepository proposalProductRepository) {
        this.proposalProductRepository = proposalProductRepository;
    }

    @Override
    public ResponseEntity<?> getAllProposalProducts() {
        return ResponseEntity.ok(proposalProductRepository.findAll());
    }

    @Override
    public ResponseEntity<?> getProposalProductById(String id) {
        try{
            if (proposalProductRepository.existsById(id)) {
                return ResponseEntity.ok(proposalProductRepository.findById(id));
            } else {
                return ResponseEntity.status(404).body("Proposal Product not found");
            }
        }catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> addProposalProduct(ProposalProduct proposalProduct) {
        try {
            var ID = IdGenerator.generateId("PROP-");
            while (proposalProductRepository.existsById(ID)) {
                ID = IdGenerator.generateId("PROP-");
            }
            proposalProduct.setId(ID);
            proposalProductRepository.save(proposalProduct);
            return ResponseEntity.ok("Proposal Product created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateProposalProduct(String id, ProposalProduct proposalProduct) {
        try {
            if (!proposalProductRepository.existsById(id)) {
                return ResponseEntity.status(404).body("Proposal Product not found");
            }
            ProposalProduct existingProposalProduct = proposalProductRepository.findById(id).orElse(null);
            if (existingProposalProduct != null) {
                existingProposalProduct.setBrand(proposalProduct.getBrand());
                existingProposalProduct.setType(proposalProduct.getType());
                existingProposalProduct.setQuantity(proposalProduct.getQuantity());
                proposalProductRepository.save(existingProposalProduct);
                return ResponseEntity.ok("Proposal Product updated successfully");
            }
            return ResponseEntity.ok("Unexpected error occurred, Proposal Product not found");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> deleteProposalProduct(String id) {
        try {
            if (proposalProductRepository.existsById(id)){
                proposalProductRepository.deleteById(id);
                return ResponseEntity.ok("Proposal Product deleted successfully");
            }
            return ResponseEntity.status(404).body("Proposal Product not found");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }
}
