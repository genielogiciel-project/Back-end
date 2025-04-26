package fst.GestionRessource.Proposal.repository;

import fst.GestionRessource.Proposal.model.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProposalRepository extends JpaRepository<Proposal, String> {
    // Custom query methods can be defined here if needed
    // For example, to find proposals by status or other attributes
    List<Proposal> findByStatus(String status);
}
