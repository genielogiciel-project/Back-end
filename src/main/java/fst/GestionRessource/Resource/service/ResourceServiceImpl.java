package fst.GestionRessource.Resource.service;

import fst.GestionRessource.Resource.model.Resource;
import fst.GestionRessource.Resource.repository.ResourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;

    public ResourceServiceImpl(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Override
    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    @Override
    public Optional<Resource> getResourceById(String id) {
        return resourceRepository.findById(id);
    }

    @Override
    public Resource addResource(Resource resource) {
        return resourceRepository.save(resource);
    }

    @Override
    public Optional<Resource> updateResource(String id, Resource resource) {
        if (resourceRepository.existsById(id)) {
            resource.setId(id);
            return Optional.of(resourceRepository.save(resource));
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteResource(String id) {
        if (resourceRepository.existsById(id)) {
            resourceRepository.deleteById(id);
            return true;
        }
        return false;
    }
}