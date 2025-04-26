package fst.GestionRessource.RequestedProduct.controller;

import fst.GestionRessource.RequestedProduct.model.RequestedProduct;
import fst.GestionRessource.RequestedProduct.service.RequestedProductServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/requested-product")
public class RequestedProductController {
    private RequestedProductServiceImpl service;

    @GetMapping
    public ResponseEntity<?> getAllRequestedProducts() {
        return service.getAllRequestedProducts();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getRequestedProductById(@PathVariable String id) {
        return service.getRequestedProductById(id);
    }
    @PostMapping("/add")
    public ResponseEntity<?> addRequestedProduct(@RequestBody RequestedProduct requestedProduct) {
        return service.addRequestedProduct(requestedProduct);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRequestedProduct(@PathVariable String id, @RequestBody RequestedProduct requestedProduct) {
        return service.updateRequestedProduct(id, requestedProduct);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRequestedProduct(@PathVariable String id) {
        return service.deleteRequestedProduct(id);
    }
}
