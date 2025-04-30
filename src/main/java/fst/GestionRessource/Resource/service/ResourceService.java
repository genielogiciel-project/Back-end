package fst.GestionRessource.Resource.service;

import fst.GestionRessource.Resource.model.Resource;

import java.util.List;
import java.util.Optional;

public interface ResourceService {
    List<Resource> getAllResources();

    Optional<Resource> getResourceById(String id);

    Resource addResource(Resource resource);

    Optional<Resource> updateResource(String id, Resource resource);

    boolean deleteResource(String id);

    List<Resource> getResourcesByUser(String userId);
}