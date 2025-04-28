package fst.GestionRessource.ProposalProduct.controller;

import fst.GestionRessource.ProposalProduct.model.ProposalProduct;
import fst.GestionRessource.ProposalProduct.service.ProposalProductServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/proposal-product")
public class ProposalProductController {
    private final ProposalProductServiceImpl service;

    public ProposalProductController(ProposalProductServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAllProposalProducts() {
        return service.getAllProposalProducts();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProposalProductById(@PathVariable String id) {
        return service.getProposalProductById(id);
    }
    @PostMapping
    public ResponseEntity<?> addProposalProduct(@RequestBody ProposalProduct proposalProduct) {
        return service.addProposalProduct(proposalProduct);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProposalProduct(@PathVariable String id, @RequestBody ProposalProduct proposalProduct) {
        return service.updateProposalProduct(id, proposalProduct);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProposalProduct(@PathVariable String id) {
        return service.deleteProposalProduct(id);
    }
}
