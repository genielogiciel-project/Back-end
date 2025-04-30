package fst.GestionRessource.CallForTender.service;

import fst.GestionRessource.CallForTender.model.CallForTender;
import fst.GestionRessource.CallForTender.repository.CallForTenderRepository;
import fst.GestionRessource.RequestedProduct.model.RequestedProduct;
import fst.GestionRessource.Utils.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CallForTenderServiceImpl implements CallForTenderService {

    private final CallForTenderRepository repository;

    @Override
    public ResponseEntity<?> getAllCallForTenders() {
        List<CallForTender> all = repository.findAll();
        return ResponseEntity.ok(all);
    }

    @Override
    public ResponseEntity<?> getCallForTenderById(String id) {
        Optional<CallForTender> result = repository.findById(id);
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<?> addCallForTender(CallForTender callForTender) {
        // Generate unique ID
        var ID = IdGenerator.generateId("CALL-");
        while (repository.existsById(ID)) {
            ID = IdGenerator.generateId("CALL-");
        }
        callForTender.setId(ID);

        // Set parent reference in each RequestedProduct
        if (callForTender.getRequestedProducts() != null) {
            for (RequestedProduct product : callForTender.getRequestedProducts()) {
                product.setCallForTender(callForTender); // 🔁 Link child to parent
            }
        }

        CallForTender saved = repository.save(callForTender);
        return ResponseEntity.ok(saved);
    }

    @Override
    public ResponseEntity<?> updateCallForTender(String id, CallForTender updatedTender) {
        return repository.findById(id).map(existing -> {
            updatedTender.setId(id); // preserve original ID
            CallForTender saved = repository.save(updatedTender);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<?> deleteCallForTender(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok("Call for Tender deleted successfully.");
        }
        return ResponseEntity.notFound().build();
    }
}
