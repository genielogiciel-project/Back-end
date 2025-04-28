package fst.GestionRessource.Proposal.controller;

import fst.GestionRessource.Proposal.model.Proposal;
import fst.GestionRessource.Proposal.service.ProposalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/proposal")
public class ProposalController {
    private ProposalService service;

    @GetMapping
    public ResponseEntity<?> getAllProposals() {
        return service.getAllProposals();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProposalById(@PathVariable String id) {
        return service.getProposalById(id);
    }
    @PostMapping
    public ResponseEntity<?> addProposal(@RequestBody Proposal proposal) {
        return service.addProposal(proposal);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProposal(@PathVariable String id, @RequestBody Proposal proposal) {
        return service.updateProposal(id, proposal);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProposal(@PathVariable String id) {
        return service.deleteProposal(id);
    }
}
