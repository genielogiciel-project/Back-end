package fst.GestionRessource.ResourceRequest.service;

import fst.GestionRessource.Department.model.Department;
import fst.GestionRessource.RequestedProduct.repository.RequestedProductRepository;
import fst.GestionRessource.RequestedProduct.service.RequestedProductService;
import fst.GestionRessource.ResourceRequest.model.ResourceRequest;
import fst.GestionRessource.ResourceRequest.model.Status;
import fst.GestionRessource.ResourceRequest.repository.ResourceRequestRepository;
import fst.GestionRessource.User.model.Role;
import fst.GestionRessource.User.model.User;
import fst.GestionRessource.User.repository.UserRepository;
import fst.GestionRessource.Utils.IdGenerator;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResourceRequestServiceImpl implements ResourceRequestService {
  private final ResourceRequestRepository repository;
  private final RequestedProductRepository requestedProductRepository;
  private final RequestedProductService requestedProductService;
  private final UserRepository userRepository;
  
  @Override
  public ResponseEntity<?> getAllResourceRequests() {
    return ResponseEntity.ok(repository.findAll());
  }
  
  @Override
  public ResponseEntity<?> getAllResourceRequests(String userId) {
    var user = userRepository.findById(userId).orElse(null);
	 
    assert user != null;
    if (user.getRole().contains(Role.TEACHER)) {
      return ResponseEntity.ok(repository.findAllByTeacherId(userId));
    } else if (user.getRole().contains(Role.DEPARTMENT_HEAD)) {
      return ResponseEntity.ok(repository.findAllByDepartmentId(user.getDepartmentHead().getId()));
    } else if (user.getRole().contains(Role.RESOURCE_MANAGER)) {
      System.out.println(repository.findAllByStatus(Status.VALIDATED));
      return ResponseEntity.ok(repository.findAllByStatus(Status.VALIDATED));
    }
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
            var tab = resourceRequest.getRequestedProducts();
            while (repository.existsById(ID)) {
              ID = IdGenerator.generateId("RESREQ-");
            }
            resourceRequest.setId(ID);
            resourceRequest.setStatus(Status.SUBMITTED);
            resourceRequest.setCreatedAt(LocalDate.now());
            resourceRequest.setRequestedProducts(null);
            repository.save(resourceRequest);


            for (var requestedProduct : tab) {
              // ID = IdGenerator.generateId("REQPROD-");
              // while (requestedProductRepository.existsById(ID)) {
              //   ID = IdGenerator.generateId("REQPROD-");
              // }
              requestedProduct.setResourceRequest(resourceRequest);
              requestedProductService.addRequestedProduct(requestedProduct);
              // requestedProduct.setId(ID);
              // requestedProductRepository.save(requestedProduct);
            }

            // System.out.println(resourceRequest);

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
              if (resourceRequest.getStatus() != null)
                existingRequest.get().setStatus(resourceRequest.getStatus());
              if (resourceRequest.getDepartment() != null)
                existingRequest.get().setDepartment(resourceRequest.getDepartment());
              if (resourceRequest.getTeacher() != null)
                existingRequest.get().setTeacher(resourceRequest.getTeacher());
              if (resourceRequest.getRequestedProducts() != null)
                existingRequest.get().setRequestedProducts(resourceRequest.getRequestedProducts());// Might not be needed
              for (var requestedProduct : resourceRequest.getRequestedProducts()) {
                if (requestedProduct.getId() == null) {
                  var ID = IdGenerator.generateId("REQPROD-");
                  while (requestedProductRepository.existsById(ID)) {
                    ID = IdGenerator.generateId("REQPROD-");
                  }
                  requestedProduct.setId(ID);
                }
                requestedProduct.setResourceRequest(existingRequest.get());
              }
                // System.out.println(existingRequest.get().getRequestedProducts());
              repository.save(existingRequest.get());
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
      } catch (Exception e) {
        return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
      }
    }
    
    @Override
    public ResponseEntity<?> updateResourceRequestStatus(String id, Status status) {
      var existingRequest = repository.findById(id);
      
      if (existingRequest.isPresent()) {
        existingRequest.get().setStatus(status);
        var savedRequest = repository.save(existingRequest.get());
        return ResponseEntity.ok(savedRequest);
      }
      return ResponseEntity.status(404).body("ResourceRequest not found");
    }
}
