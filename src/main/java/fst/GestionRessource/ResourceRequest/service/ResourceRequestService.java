package fst.GestionRessource.ResourceRequest.service;

import fst.GestionRessource.Department.model.Department;
import fst.GestionRessource.ResourceRequest.model.ResourceRequest;
import fst.GestionRessource.ResourceRequest.model.Status;
import fst.GestionRessource.User.model.User;

import org.springframework.http.ResponseEntity;

public interface ResourceRequestService
{
    ResponseEntity<?> getAllResourceRequests();
    
    ResponseEntity<?> getAllResourceRequests(String userId);

    ResponseEntity<?> getResourceRequestById(String id);

    ResponseEntity<?> addResourceRequest(ResourceRequest resourceRequest);

    ResponseEntity<?> updateResourceRequest(String id, ResourceRequest resourceRequest);

    ResponseEntity<?> deleteResourceRequest(String id);

    ResponseEntity<?> getResourceRequestByDept(Department dept);

    ResponseEntity<?> getResourceRequestByUser(User user);
    
    ResponseEntity<?> updateResourceRequestStatus(String id, Status status);
}
