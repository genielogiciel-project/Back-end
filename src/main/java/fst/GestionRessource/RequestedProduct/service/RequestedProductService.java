package fst.GestionRessource.RequestedProduct.service;

import fst.GestionRessource.RequestedProduct.model.RequestedProduct;
import org.springframework.http.ResponseEntity;

public interface RequestedProductService {
    public ResponseEntity<?> getAllRequestedProducts();

    public ResponseEntity<?> getRequestedProductById(String id);

    public ResponseEntity<?> addRequestedProduct(RequestedProduct requestedProduct);

    public ResponseEntity<?> updateRequestedProduct(String id, RequestedProduct requestedProduct);

    public ResponseEntity<?> deleteRequestedProduct(String id);
}
