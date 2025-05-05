package fst.GestionRessource.CallForTender.service;

import fst.GestionRessource.CallForTender.model.CallForTender;
import fst.GestionRessource.CallForTender.model.CallRequest;
import fst.GestionRessource.CallForTender.repository.CallForTenderRepository;
import fst.GestionRessource.RequestedProduct.model.RequestedProduct;
import fst.GestionRessource.RequestedProduct.repository.RequestedProductRepository;
import fst.GestionRessource.ResourceRequest.model.ResourceRequest;
import fst.GestionRessource.ResourceRequest.model.Status;
import fst.GestionRessource.ResourceRequest.repository.ResourceRequestRepository;
import fst.GestionRessource.ResourceRequest.service.ResourceRequestService;
import fst.GestionRessource.Utils.IdGenerator;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CallForTenderServiceImpl implements CallForTenderService {

  @Autowired
  private final CallForTenderRepository callForTenderRepository;
  @Autowired
  private final RequestedProductRepository requestedProductRepository;
  @Autowired
  private final ResourceRequestRepository resourceRequestRepository;
	@Autowired
	private ResourceRequestService resourceRequestService;
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
        callForTender.setOpen(true);
        callForTender.getRequestedProducts().forEach(product -> {
          var resourceId = product.getResourceRequest().getId();
          product.setCallForTender(callForTender);
          product.setResourceRequest((ResourceRequest) resourceRequestService.updateResourceRequestStatus(resourceId, Status.SENT).getBody());
        });
        
      callForTenderRepository.save(callForTender);
      
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

    @Override
    public ResponseEntity<?> getRequestedProductsForCallForTender() {
      var requests = resourceRequestRepository.findAllByStatus(Status.VALIDATED);
      var products = new ArrayList<RequestedProduct>();

      for (var request : requests) {
        products.addAll(request.getRequestedProducts());
      }

      return ResponseEntity.ok(products);
    }
    
    @Override
    public ResponseEntity<?> updateCallForTenderStatus(String id) {
      var call = callForTenderRepository.findById(id);
      
      if (call.isPresent()) {
        call.get().setOpen(!call.get().getOpen());
        callForTenderRepository.save(call.get());
        return ResponseEntity.ok("Call for Tender updated successfully.");
      }
      
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Call for Tender not found.");
    }
}
