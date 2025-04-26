package fst.GestionRessource.RequestedProduct.service;

import fst.GestionRessource.RequestedProduct.model.RequestedProduct;
import fst.GestionRessource.RequestedProduct.repository.RequestedProductRepository;
import fst.GestionRessource.Utils.IdGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RequestedProductServiceImpl implements RequestedProductService{

    private RequestedProductRepository requestedProductRepository;
    @Override
    public ResponseEntity<?> getAllRequestedProducts() {
        return ResponseEntity.ok(requestedProductRepository.findAll());
    }

    @Override
    public ResponseEntity<?> getRequestedProductById(String id) {
        try {
            if (!requestedProductRepository.existsById(id)) {
                return ResponseEntity.status(404).body("RequestedProduct not found");
            }
            return ResponseEntity.ok(requestedProductRepository.findById(id));
        }catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> addRequestedProduct(RequestedProduct requestedProduct) {
        try {

            var ID = IdGenerator.generateId("REQPROD-");
            while (requestedProductRepository.existsById(ID)) {
                ID = IdGenerator.generateId("REQPROD-");
            }
            requestedProduct.setId(ID);
            requestedProductRepository.save(requestedProduct);
            return ResponseEntity.ok("RequestedProduct created successfully");

        }catch (Exception e){
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateRequestedProduct(String id, RequestedProduct requestedProduct) {
        try {
            if (!requestedProductRepository.existsById(id)) {
                return ResponseEntity.status(404).body("RequestedProduct not found");
            }
            RequestedProduct existingRequestedProduct = requestedProductRepository.findById(id).orElse(null);
            if (existingRequestedProduct != null) {
                existingRequestedProduct.setBrand(requestedProduct.getBrand());
                existingRequestedProduct.setType(requestedProduct.getType());
                existingRequestedProduct.setQuantity(requestedProduct.getQuantity());
                requestedProductRepository.save(existingRequestedProduct);
                return ResponseEntity.ok("RequestedProduct updated successfully");
            }
            return ResponseEntity.status(500).body("Unexpected error occurred");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> deleteRequestedProduct(String id) {
        try {
            if (requestedProductRepository.existsById(id)){
                requestedProductRepository.deleteById(id);
                return ResponseEntity.ok("RequestedProduct deleted successfully");
            }
            return ResponseEntity.status(404).body("RequestedProduct not found");
        }catch (Exception e){
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }
}
