package fst.GestionRessource.CallForTender.service;

import fst.GestionRessource.CallForTender.model.CallForTender;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CallForTenderServiceImpl implements CallForTenderService {

    private final List<CallForTender> callForTenderList = new ArrayList<>();

    @Override
    public ResponseEntity<?> getAllCallForTenders() {
        return ResponseEntity.ok(callForTenderList);
    }

    @Override
    public ResponseEntity<?> getCallForTenderById(String id) {
        Optional<CallForTender> callForTender = callForTenderList.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
        return callForTender.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<?> addCallForTender(CallForTender callForTender) {
        callForTenderList.add(callForTender);
        return ResponseEntity.ok("Call for Tender added successfully.");
    }

    @Override
    public ResponseEntity<?> updateCallForTender(String id, CallForTender callForTender) {
        for (int i = 0; i < callForTenderList.size(); i++) {
            if (callForTenderList.get(i).getId().equals(id)) {
                callForTenderList.set(i, callForTender);
                return ResponseEntity.ok("Call for Tender updated successfully.");
            }
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<?> deleteCallForTender(String id) {
        boolean removed = callForTenderList.removeIf(c -> c.getId().equals(id));
        if (removed) {
            return ResponseEntity.ok("Call for Tender deleted successfully.");
        }
        return ResponseEntity.notFound().build();
    }
}