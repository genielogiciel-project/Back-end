package fst.GestionRessource.Resource.service;

import fst.GestionRessource.Resource.model.Resource;
import fst.GestionRessource.Resource.repository.ResourceRepository;
import fst.GestionRessource.User.repository.UserRepository;
import fst.GestionRessource.Utils.IdGenerator;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final UserRepository userRepository;

    public ResourceServiceImpl(ResourceRepository resourceRepository, UserRepository userRepository) {
        this.resourceRepository = resourceRepository;
        this.userRepository = userRepository;
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
      var ID = IdGenerator.generateId("RES-");
      while (resourceRepository.existsById(ID)) {
        ID = IdGenerator.generateId("RES-");
      }
      resource.setId(ID);

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

    @Override
    public List<Resource> getResourcesByUser(String userId) {

        return resourceRepository.getResourcesByUser(userRepository.findById(userId).orElse(null));
    }
}
