package fst.GestionRessource.Proposal.service;

import fst.GestionRessource.Proposal.model.Proposal;
import fst.GestionRessource.Proposal.repository.ProposalRepository;
import fst.GestionRessource.Utils.IdGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProposalServiceImpl implements ProposalService{

    private ProposalRepository proposalRepository;
    @Override
    public ResponseEntity<?> getAllProposals() {
        return ResponseEntity.ok(proposalRepository.findAll());
    }

    @Override
    public ResponseEntity<?> getProposalById(String id) {
        try {
            Proposal proposal = proposalRepository.findById(id).orElseThrow(() -> new Exception("Proposal not found"));
            return ResponseEntity.ok(proposal);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Proposal not found");
        }
    }

    @Override
    public ResponseEntity<?> addProposal(Proposal proposal) {
        try {
            var ID = IdGenerator.generateId("PROP-");
            while (proposalRepository.existsById(ID)) {
                System.out.println("test hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                ID = IdGenerator.generateId("PROP-");
            }
            proposal.setId(ID);
            proposalRepository.save(proposal);
            return ResponseEntity.ok("Proposal created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateProposal(String id, Proposal proposal) {
        try {
            if (!proposalRepository.existsById(id)) {
                return ResponseEntity.status(404).body("Proposal not found");
            }
            Proposal existingProposal = proposalRepository.findById(id).orElse(null);
            if (existingProposal != null) {
                existingProposal.setDeliveryDate(proposal.getDeliveryDate());
                existingProposal.setWarranty(proposal.getWarranty());
                existingProposal.setAccepted(proposal.getAccepted());
                existingProposal.setTotalPrice(proposal.calculateTotalPrice());
                proposalRepository.save(existingProposal);
                return ResponseEntity.ok("Proposal updated successfully");
            }
            return ResponseEntity.ok("Unexpected error occurred, Proposal not found");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> deleteProposal(String id) {
        try {
            if (proposalRepository.existsById(id)) {
                proposalRepository.deleteById(id);
                return ResponseEntity.ok("Proposal deleted successfully");
            } else {
                return ResponseEntity.status(404).body("Proposal not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }
}
