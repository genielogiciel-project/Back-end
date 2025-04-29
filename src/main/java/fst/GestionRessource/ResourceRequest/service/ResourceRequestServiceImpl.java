package fst.GestionRessource.ResourceRequest.service;

import fst.GestionRessource.Department.model.Department;
import fst.GestionRessource.ResourceRequest.model.ResourceRequest;
import fst.GestionRessource.ResourceRequest.repository.ResourceRequestRepository;
import fst.GestionRessource.User.model.User;
import fst.GestionRessource.Utils.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResourceRequestServiceImpl implements ResourceRequestService {
  private final ResourceRequestRepository repository;

    @Override
    public ResponseEntity<?> getAllResourceRequests() {
        return ResponseEntity.ok(repository.findAll());
    }

    @Override
    public ResponseEntity<?> getResourceRequestById(String id) {
        try {
            if (!repository.existsById(id)) {
                return ResponseEntity.status(404).body("ResourceRequest not found");
            }
            return ResponseEntity.ok(repository.findById(id));
        }catch(Exception e){
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> addResourceRequest(ResourceRequest resourceRequest) {
        try {
            var ID = IdGenerator.generateId("RESREQ-");
            while (repository.existsById(ID)) {
                ID = IdGenerator.generateId("RESREQ-");
            }
            resourceRequest.setId(ID);
            repository.save(resourceRequest);
            return ResponseEntity.ok("ResourceRequest created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateResourceRequest(String id, ResourceRequest resourceRequest) {
        try {
            var existingRequest = repository.findById(id);
            if (existingRequest.isPresent()) {
                existingRequest.get().setStatus(resourceRequest.getStatus());
                existingRequest.get().setRequestedProducts(resourceRequest.getRequestedProducts());// Might not be needed
                return ResponseEntity.ok("ResourceRequest updated successfully");
            }
            return ResponseEntity.ok("Unexpected error occurred , Resource not found");
        }catch (Exception e){
            return ResponseEntity.status(500).body("An error occurred" + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> deleteResourceRequest(String id) {
        try {
            if (repository.existsById(id)){
                repository.deleteById(id);
                return ResponseEntity.ok("ResourceRequest deleted successfully");
            }
            return ResponseEntity.status(500).body("Unexpected error occurred ");
        }catch (Exception e){
            return ResponseEntity.status(500).body("An error occurred" + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getResourceRequestByDept(Department dept) {
        try {
            if (!repository.existsByDepartment(dept)) {
                return ResponseEntity.status(404).body("ResourceRequest not found");
            }
            return ResponseEntity.ok(repository.getResourceRequestsByDepartment(dept));
        }catch (Exception e){
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getResourceRequestByUser(User user) {
        try {
            if (!repository.existsByTeacher(user)) {
                return ResponseEntity.status(404).body("ResourceRequest not found");
            }
            return ResponseEntity.ok(repository.getResourceRequestsByUser(user));
        }catch (Exception e){
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getResourceRequestByStatus(String status) {
        try {
            if (!repository.existsByStatus(status)) {
                return ResponseEntity.status(404).body("ResourceRequest not found");
            }
            return ResponseEntity.ok(repository.getResourceRequestsByStatus(status));
        }catch (Exception e){
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }
}
