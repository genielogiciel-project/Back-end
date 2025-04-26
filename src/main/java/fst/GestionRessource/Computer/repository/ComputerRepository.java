package fst.GestionRessource.Computer.repository;

import fst.GestionRessource.Computer.model.Computer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComputerRepository extends JpaRepository<Computer, String> {
}