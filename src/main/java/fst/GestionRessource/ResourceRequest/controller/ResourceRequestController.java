package fst.GestionRessource.ResourceRequest.controller;

import fst.GestionRessource.ResourceRequest.model.ResourceRequest;
import fst.GestionRessource.ResourceRequest.service.ResourceRequestServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resource-request")
public class ResourceRequestController {
    private ResourceRequestServiceImpl service;

    @GetMapping
    public ResponseEntity<?> getAllResourceRequests() {
        return service.getAllResourceRequests();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getResourceRequestById(@PathVariable String id) {
        return service.getResourceRequestById(id);
    }
    @PostMapping
    public ResponseEntity<?> addResourceRequest(@RequestBody ResourceRequest resourceRequest) {
        return service.addResourceRequest(resourceRequest);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateResourceRequest(@PathVariable String id, @RequestBody ResourceRequest resourceRequest) {
        return service.updateResourceRequest(id, resourceRequest);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResourceRequest(@PathVariable String id) {
        return service.deleteResourceRequest(id);
    }
}
