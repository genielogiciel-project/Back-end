package fst.GestionRessource.CallForTender.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fst.GestionRessource.CallForTender.model.CallForTender;
import fst.GestionRessource.CallForTender.service.CallForTenderServiceImpl;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/call-for-tender")
@RequiredArgsConstructor
public class CallForTenderController {
    private final CallForTenderServiceImpl service;

    @GetMapping
    public ResponseEntity<?> getAllCallForTenders() {
      return service.getAllCallForTenders();
    }

    @GetMapping("/requested-products")
    public ResponseEntity<?> getRequestedProductsForCallForTender() {
      return service.getRequestedProductsForCallForTender();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCallForTenderById(@PathVariable String id) {
        return service.getCallForTenderById(id);
    }

    @PostMapping
    public ResponseEntity<?> addCallForTender(@RequestBody CallForTender callForTender) {
      return service.addCallForTender(callForTender);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCallForTender(@PathVariable String id, @RequestBody CallForTender callForTender) {
        return service.updateCallForTender(id, callForTender);
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateCallForTenderStatus(@PathVariable String id) {
        return service.updateCallForTenderStatus(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCallForTender(@PathVariable String id) {
        return service.deleteCallForTender(id);
    }
}
