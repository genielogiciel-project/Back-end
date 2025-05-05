package fst.GestionRessource.Proposal.service;

import fst.GestionRessource.Proposal.model.Proposal;
import org.springframework.http.ResponseEntity;

public interface ProposalService {
    public ResponseEntity<?> getAllProposals();

    public ResponseEntity<?> getProposalById(String id);

    public ResponseEntity<?> addProposal(Proposal proposal);

    public ResponseEntity<?> updateProposal(String id, Proposal proposal);

    public ResponseEntity<?> deleteProposal(String id);
    
    public ResponseEntity<?> acceptedRefused(String resManagerId, String id, String[] refused);
}
