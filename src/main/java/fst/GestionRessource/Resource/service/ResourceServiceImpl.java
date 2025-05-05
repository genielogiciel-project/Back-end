package fst.GestionRessource.Resource.service;

import fst.GestionRessource.Resource.model.Resource;
import fst.GestionRessource.Resource.repository.ResourceRepository;
import fst.GestionRessource.User.model.Role;
import fst.GestionRessource.User.repository.UserRepository;
import fst.GestionRessource.Utils.IdGenerator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<?> getAllResources(String userID) {
      var user = userRepository.findById(userID);
      var resources = resourceRepository.findAll();
      var finalResources = new ArrayList<Resource>();
      
      if (user.isPresent()) {
        if (user.get().getRole().contains(Role.TEACHER)) {
          resources.forEach(resource -> {
            if (resource.getUser() != null) {
              if (resource.getUser().getId().equals(userID)){
                finalResources.add(resource);
              }
            }
          });
          resources.forEach(resource -> {
            if (resource.getDepartment() != null) {
              if (resource.getDepartment().getId().equals(user.get().getDepartment().getId()))
                finalResources.add(resource);
            }
          });
          
          return ResponseEntity.ok(finalResources);
        }
      }
      
      return ResponseEntity.ok(resources);
    }

    @Override
    public ResponseEntity<?> getResourceById(String id) {
        return ResponseEntity.ok(resourceRepository.findById(id));
    }

    @Override
    public ResponseEntity<?> addResource(Resource resource) {
      var ID = IdGenerator.generateId("RES-");
      while (resourceRepository.existsById(ID)) {
        ID = IdGenerator.generateId("RES-");
      }
      resource.setId(ID);
      resource.setAcquisitionDate(LocalDate.now());
      resource.setInventoryNumber(IdGenerator.generateId("INV-"));
      
      return ResponseEntity.ok(resourceRepository.save(resource));
    }

    @Override
    public ResponseEntity<?> updateResource(String id, Resource resource) {
        if (resourceRepository.existsById(id)) {
            resource.setId(id);
            return ResponseEntity.ok(resourceRepository.save(resource));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<?> deleteResource(String id) {
        if (resourceRepository.existsById(id)) {
            resourceRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
