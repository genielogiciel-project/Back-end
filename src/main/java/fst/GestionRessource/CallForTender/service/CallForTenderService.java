package fst.GestionRessource.CallForTender.service;

import fst.GestionRessource.CallForTender.model.CallForTender;
import org.springframework.http.ResponseEntity;

public interface CallForTenderService {
    public ResponseEntity<?> getAllCallForTenders();

    public ResponseEntity<?> getCallForTenderById(String id);

    public ResponseEntity<?> addCallForTender(CallForTender callForTender);

    public ResponseEntity<?> updateCallForTender(String id, CallForTender callForTender);

    public ResponseEntity<?> deleteCallForTender(String id);
}