package fst.GestionRessource.Proposal.service;

import fst.GestionRessource.CallForTender.repository.CallForTenderRepository;
import fst.GestionRessource.Notification.model.Notification;
import fst.GestionRessource.Notification.model.NotificationType;
import fst.GestionRessource.Notification.service.NotificationService;
import fst.GestionRessource.Proposal.model.Proposal;
import fst.GestionRessource.Proposal.repository.ProposalRepository;
import fst.GestionRessource.ProposalProduct.repository.ProposalProductRepository;
import fst.GestionRessource.Resource.model.Resource;
import fst.GestionRessource.Resource.model.ResourceStatus;
import fst.GestionRessource.Resource.service.ResourceService;
import fst.GestionRessource.Supplier.model.Supplier;
import fst.GestionRessource.User.model.User;
import fst.GestionRessource.User.repository.UserRepository;
import fst.GestionRessource.Utils.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class ProposalServiceImpl implements ProposalService{

    private final ProposalRepository proposalRepository;
    private final ProposalProductRepository proposalProductRepository;
    private final CallForTenderRepository callForTenderRepository;
    private final NotificationService notificationService;
    private final UserRepository userRepository;
    private final ResourceService resourceService;

    @Override
    public ResponseEntity<?> getAllProposals() {
        return ResponseEntity.ok(proposalRepository.findAll());
    }

    @Override
    public ResponseEntity<?> getProposalById(String id) {
        try {
            Proposal proposal = proposalRepository.findById(id).orElseThrow(() -> new Exception("Proposal not found"));
            return ResponseEntity.ok(proposal);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Proposal not found");
        }
    }

    @Override
    public ResponseEntity<?> addProposal(Proposal proposal) {
        try {
            AtomicReference<String> ID = new AtomicReference<>(IdGenerator.generateId("PRO-"));
            while (proposalRepository.existsById(ID.get())){
                ID.set(IdGenerator.generateId("PRO-"));
            }

            proposal.setId(ID.get());
            
            var products = proposal.getProposalProducts();
            proposal.setProposalProducts(null);
            var savedProposal = proposalRepository.save(proposal);
//            System.out.println(savedProposal);
            products.forEach(product -> {
                ID.set(IdGenerator.generateId("PRO_PRODUCT-"));
                product.setProposal(savedProposal);
//                product.setProposal(proposal);
                while (proposalProductRepository.existsById(ID.get())) {
                    ID.set(IdGenerator.generateId("PRO_PRODUCT-"));
                }
                product.setId(ID.get());
                proposalProductRepository.save(product);
            });
//            proposalRepository.save(proposal);
//            System.out.println(proposal);
            return ResponseEntity.ok("Proposal created successfully");
        } catch (Exception e) {
            e.printStackTrace(); // Print the full stack trace for debugging
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateProposal(String id, Proposal proposal) {
        try {
            if (!proposalRepository.existsById(id)) {
                return ResponseEntity.status(404).body("Proposal not found");
            }
            Proposal existingProposal = proposalRepository.findById(id).orElse(null);
            if (existingProposal != null) {
                existingProposal.setDeliveryDate(proposal.getDeliveryDate());
                existingProposal.setWarranty(proposal.getWarranty());
                existingProposal.setAccepted(proposal.getAccepted());
                existingProposal.setTotalPrice(proposal.calculateTotalPrice());
                proposalRepository.save(existingProposal);
                return ResponseEntity.ok("Proposal updated successfully");
            }
            return ResponseEntity.ok("Unexpected error occurred, Proposal not found");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> deleteProposal(String id) {
        try {
            if (proposalRepository.existsById(id)) {
                proposalRepository.deleteById(id);
                return ResponseEntity.ok("Proposal deleted successfully");
            } else {
                return ResponseEntity.status(404).body("Proposal not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }
    
    @Override
    public ResponseEntity<?> acceptedRefused(String resManagerId, String acceptedId, String[] refused) {
        var accepted = proposalRepository.findById(acceptedId);
        var sender = userRepository.findById(resManagerId);
        var receiver = userRepository.findById(accepted.get().getSupplier().getId());
        var call = callForTenderRepository.findById(accepted.get().getCallForTender().getId());
        var notif = new Notification(null, "Ton proposition de l'appel d'ofre '"+ call.get().getTitle() +"' a etait accepte", null, null, NotificationType.SUCCESS, sender.get(), receiver.get());
        
        
        if (accepted.isPresent()) {
            accepted.get().setAccepted(true);
            notificationService.addNotification(notif);
            proposalRepository.save(accepted.get());
        }
        
        for (String id : refused) {
            var refusedProposal = proposalRepository.findById(id);
            receiver = userRepository.findById(refusedProposal.get().getSupplier().getId());
            
            notif.setType(NotificationType.REJECTION);
            notif.setMessage("Ton proposition de l'appel d'ofre '"+ call.get().getTitle() +"' a etait refuse");
            notif.setReceiver(receiver.get());
            
            notificationService.addNotification(notif);
            if (refusedProposal.isPresent()) {
                refusedProposal.get().setAccepted(false);
                proposalRepository.save(refusedProposal.get());
            }
        }
        
        if (call.isPresent()) {
            call.get().setOpen(false);
            callForTenderRepository.save(call.get());
        }
        
        notif.setType(NotificationType.DELIVERY);
        notif.setMessage("La livraison des resources d'appel d'offre '" + call.get().getTitle() + "' est faite");
        sender = userRepository.findById(accepted.get().getSupplier().getId());
        receiver = userRepository.findById(resManagerId);
        notif.setSender(sender.get());
        notif.setReceiver(receiver.get());
        notificationService.addNotification(notif);
        
        call.get().getRequestedProducts().forEach(requestedProduct -> {
            for (int i = 0; i < requestedProduct.getQuantity(); i++) {
                var resource = new Resource(
                  null,
                  null,
                  requestedProduct.getType(),
                  requestedProduct.getBrand(),
                  requestedProduct.getSpecifications(),
                  ResourceStatus.AVAILABLE,
                  null,
                  LocalDate.now().plusMonths(accepted.get().getWarranty()),
                  (Supplier) userRepository.findById(accepted.get().getSupplier().getId()).get(),
                  null,
                  null,
                  null);
                resourceService.addResource(resource);
            }
        });
        
        return ResponseEntity.ok("Proposal accepted successfully");
    }
}
