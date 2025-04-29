package fst.GestionRessource.ResourceRequest.repository;

import fst.GestionRessource.Department.model.Department;
import fst.GestionRessource.ResourceRequest.model.ResourceRequest;
import fst.GestionRessource.User.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ResourceRequestRepository extends JpaRepository<ResourceRequest, String> {
    boolean existsByDepartment(Department department);

    @Query("SELECT r FROM ResourceRequest r WHERE r.department = ?1")
    List<ResourceRequest> getResourceRequestsByDepartment(Department dept);
    // Custom query methods can be defined here if needed
    @Query("SELECT r FROM ResourceRequest r WHERE r.teacher = ?1")
    List<ResourceRequest> getResourceRequestsByUser(User user);

    boolean existsByTeacher(User teacher);

}
