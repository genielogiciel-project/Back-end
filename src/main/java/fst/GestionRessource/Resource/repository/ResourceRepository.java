package fst.GestionRessource.Resource.repository;

import fst.GestionRessource.Resource.model.Resource;
import fst.GestionRessource.User.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, String> {
    List<Resource> getResourcesByUser(User user);
}