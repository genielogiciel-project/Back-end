package fst.GestionRessource.CallForTender.service;

import fst.GestionRessource.CallForTender.model.CallForTender;
import fst.GestionRessource.CallForTender.repository.CallForTenderRepository;
import fst.GestionRessource.RequestedProduct.repository.RequestedProductRepository;
import fst.GestionRessource.ResourceRequest.model.Status;
import fst.GestionRessource.Utils.IdGenerator;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CallForTenderServiceImpl implements CallForTenderService {

  @Autowired
  private final CallForTenderRepository callForTenderRepository;

  @Autowired
  private final RequestedProductRepository requestedProductRepository;
    // private final List<CallForTender> callForTenderList = new ArrayList<>();

    @Override
    public ResponseEntity<?> getAllCallForTenders() {
        // return ResponseEntity.ok(callForTenderList);
        return ResponseEntity.ok(callForTenderRepository.findAll());

    }

    @Override
    public ResponseEntity<?> getCallForTenderById(String id) {

      // Optional<CallForTender> callForTender = callForTenderList.stream()
      //         .filter(c -> c.getId().equals(id))
      //         .findFirst();
      // return callForTender.map(ResponseEntity::ok)
      //         .orElse(ResponseEntity.notFound().build());
      return ResponseEntity.ok(callForTenderRepository.findById(id));
    }

    @Override
    public ResponseEntity<?> addCallForTender(CallForTender callForTender) {

      // callForTenderList.add(callForTender);
        var ID = IdGenerator.generateId("CFT-");
        while (callForTenderRepository.existsById(ID)) {
            ID = IdGenerator.generateId("CFT-");
        }
        callForTender.setId(ID);
        var savedCallForTender = callForTenderRepository.save(callForTender);
        callForTender.getRequestedProducts().forEach(product -> product.getResourceRequest().setStatus(Status.SENT));
        callForTender.getRequestedProducts().forEach(product -> product.setCallForTender(savedCallForTender));
        System.out.println(callForTender.getRequestedProducts());
        callForTender.getRequestedProducts().forEach(product -> requestedProductRepository.save(product));

        return ResponseEntity.ok("Call for Tender added successfully.");
    }

    @Override
    public ResponseEntity<?> updateCallForTender(String id, CallForTender callForTender) {
      // for (int i = 0; i < callForTenderList.size(); i++) {
      //     if (callForTenderList.get(i).getId().equals(id)) {
      //         callForTenderList.set(i, callForTender);
      //         return ResponseEntity.ok("Call for Tender updated successfully.");
      //     }
      // }
      var existingCallForTender = callForTenderRepository.findById(id);
      if (existingCallForTender.isPresent()) {
        if (callForTender.getOpen() != null) {
          existingCallForTender.get().setOpen(callForTender.getOpen());
        }
        if (callForTender.getTitle() != null) {
          existingCallForTender.get().setTitle(callForTender.getTitle());
        }
        if (callForTender.getStartDate() != null) {
          existingCallForTender.get().setStartDate(callForTender.getStartDate());
        }
        if (callForTender.getEndDate() != null) {
          existingCallForTender.get().setEndDate(callForTender.getEndDate());
        }
        if (callForTender.getProposals() != null) {
          existingCallForTender.get().setProposals(callForTender.getProposals());
        }
        if (callForTender.getRequestedProducts() != null) {
          existingCallForTender.get().setRequestedProducts(callForTender.getRequestedProducts());
        }
        if (callForTender.getResourceManager() != null) {
          existingCallForTender.get().setResourceManager(callForTender.getResourceManager());
        }

        callForTenderRepository.save(callForTender);
        return ResponseEntity.ok("Call for Tender updated successfully.");
      }

      return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<?> deleteCallForTender(String id) {

        if (callForTenderRepository.existsById(id)) {
            callForTenderRepository.deleteById(id);
            return ResponseEntity.ok("Call for Tender deleted successfully.");
        }
        return ResponseEntity.notFound().build();
    }
}
