package fst.GestionRessource.User.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fst.GestionRessource.User.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    List<User> findAllByDepartment_Id(String department_id);
    Optional<User> findByUserNumber(String userNumber);
    User findUserById(String id);
}
