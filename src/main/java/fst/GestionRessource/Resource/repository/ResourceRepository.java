package fst.GestionRessource.Resource.repository;

import fst.GestionRessource.Resource.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, String> {
}