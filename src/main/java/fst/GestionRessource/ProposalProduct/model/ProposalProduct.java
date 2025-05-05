package fst.GestionRessource.ProposalProduct.model;

import fst.GestionRessource.Proposal.model.Proposal;
import fst.GestionRessource.Resource.model.ResourceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProposalProduct {
  @Id
  private String id;
  @Enumerated(EnumType.STRING)
  private ResourceType type;
  private String brand;
  private Integer quantity;
  private Double unitPrice;

  @ManyToOne
  @JoinColumn(name = "proposalId")
  private Proposal proposal;
}
