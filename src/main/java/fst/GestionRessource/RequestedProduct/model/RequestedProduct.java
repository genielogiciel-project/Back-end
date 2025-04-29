package fst.GestionRessource.RequestedProduct.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fst.GestionRessource.CallForTender.model.CallForTender;
import fst.GestionRessource.Resource.model.ResourceType;
import fst.GestionRessource.ResourceRequest.model.ResourceRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestedProduct {
  @Id
  private String id;
  private ResourceType type;
  private String brand;
  private Integer quantity;
  private String specifications;

  @ManyToOne
  @JsonIgnore
  @JoinColumn(name = "resourceRequestId")
  private ResourceRequest resourceRequest;

  @ManyToOne
  @JoinColumn(name = "callForTenderId")
  private CallForTender callForTender;
}
