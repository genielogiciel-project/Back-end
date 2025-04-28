package fst.GestionRessource.Proposal.repository;

import fst.GestionRessource.Proposal.model.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProposalRepository extends JpaRepository<Proposal, String> {
    // Custom query methods can be defined here if needed
    // For example, to find proposals by status or other attributes
    Optional<Proposal> findByAccepted(Boolean status);
}
