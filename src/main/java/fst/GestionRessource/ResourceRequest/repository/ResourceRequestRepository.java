package fst.GestionRessource.ResourceRequest.repository;

import fst.GestionRessource.ResourceRequest.model.ResourceRequest;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ResourceRequestRepository extends JpaRepository<ResourceRequest, String> {
    // Custom query methods can be defined here if needed
}
