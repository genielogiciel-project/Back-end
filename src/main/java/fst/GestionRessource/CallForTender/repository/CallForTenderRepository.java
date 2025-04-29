package fst.GestionRessource.CallForTender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;

import fst.GestionRessource.CallForTender.model.CallForTender;

@Controller
public interface CallForTenderRepository extends JpaRepository<CallForTender, String> {

}
