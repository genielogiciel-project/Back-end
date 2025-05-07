package fst.GestionRessource.Resource.service;

import fst.GestionRessource.Resource.model.Resource;
import org.springframework.http.ResponseEntity;


public interface ResourceService {
    ResponseEntity<?> getAllResources(String userId);

    ResponseEntity<?> getResourceById(String id);

    ResponseEntity<?> addResource(Resource resource);

    ResponseEntity<?> updateResource(String id, Resource resource);

    ResponseEntity<?> deleteResource(String id);
}
