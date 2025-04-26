package fst.GestionRessource.ProposalProduct.controller;

import fst.GestionRessource.ProposalProduct.model.ProposalProduct;
import fst.GestionRessource.ProposalProduct.service.ProposalProductServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/proposal-product")
public class ProposalProductController {
    private ProposalProductServiceImpl service;

    @GetMapping
    public ResponseEntity<?> getAllProposalProducts() {
        return service.getAllProposalProducts();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProposalProductById(@PathVariable String id) {
        return service.getProposalProductById(id);
    }
    @GetMapping("/add")
    public ResponseEntity<?> addProposalProduct(@RequestBody ProposalProduct proposalProduct) {
        return service.addProposalProduct(proposalProduct);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProposalProduct(@PathVariable String id, @RequestBody ProposalProduct proposalProduct) {
        return service.updateProposalProduct(id, proposalProduct);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProposalProduct(@PathVariable String id) {
        return service.deleteProposalProduct(id);
    }
}
