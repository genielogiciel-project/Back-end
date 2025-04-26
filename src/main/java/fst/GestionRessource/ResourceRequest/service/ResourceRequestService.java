package fst.GestionRessource.ResourceRequest.service;

import fst.GestionRessource.ResourceRequest.model.ResourceRequest;
import org.springframework.http.ResponseEntity;

public interface ResourceRequestService
{
    public ResponseEntity<?> getAllResourceRequests();

    public ResponseEntity<?> getResourceRequestById(String id);

    public ResponseEntity<?> addResourceRequest(ResourceRequest resourceRequest);

    public ResponseEntity<?> updateResourceRequest(String id, ResourceRequest resourceRequest);

    public ResponseEntity<?> deleteResourceRequest(String id);
}
