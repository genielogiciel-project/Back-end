package fst.GestionRessource.Proposal.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fst.GestionRessource.CallForTender.model.CallForTender;
import fst.GestionRessource.ProposalProduct.model.ProposalProduct;
import fst.GestionRessource.Supplier.model.Supplier;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Proposal {
  @Id
  private String id;
  private LocalDate deliveryDate;
  private Integer warranty;
  private Double totalPrice;
  private Boolean accepted;

  @ManyToOne
  @JoinColumn(name = "supplierId")
  private Supplier supplier;

  @OneToMany(mappedBy = "proposal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnoreProperties({"proposal"})
  private List<ProposalProduct> proposalProducts;

  @ManyToOne
  @JoinColumn(name = "callForTenderId")
  private CallForTender callForTender;

  // @OneToOne(mappedBy = "selectedProposal")
  // private CallForTender selectedBy;

  public Double calculateTotalPrice() {
    double total = 0;
    for (ProposalProduct proposalProduct : proposalProducts) {
      total += proposalProduct.getQuantity() * proposalProduct.getUnitPrice();
    }
    this.totalPrice = total;
    return total;
  }
}
