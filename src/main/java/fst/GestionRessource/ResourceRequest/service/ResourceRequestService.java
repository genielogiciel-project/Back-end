package fst.GestionRessource.ResourceRequest.service;

import fst.GestionRessource.Department.model.Department;
import fst.GestionRessource.ResourceRequest.model.ResourceRequest;
import fst.GestionRessource.ResourceRequest.model.Status;
import fst.GestionRessource.User.model.User;
import fst.GestionRessource.User.model.UserRequest;

import org.springframework.http.ResponseEntity;

public interface ResourceRequestService
{
    public ResponseEntity<?> getAllResourceRequests(UserRequest user);

    public ResponseEntity<?> getResourceRequestById(String id);

    public ResponseEntity<?> addResourceRequest(ResourceRequest resourceRequest);

    public ResponseEntity<?> updateResourceRequest(String id, ResourceRequest resourceRequest);

    public ResponseEntity<?> deleteResourceRequest(String id);

    public ResponseEntity<?> getResourceRequestByDept(Department dept);

    public ResponseEntity<?> getResourceRequestByUser(User user);
    
    public ResponseEntity<?> updateResourceRequestStatus(String id, Status status);
}
