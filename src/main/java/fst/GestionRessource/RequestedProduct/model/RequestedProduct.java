package fst.GestionRessource.RequestedProduct.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import fst.GestionRessource.CallForTender.model.CallForTender;
import fst.GestionRessource.Resource.model.ResourceType;
import fst.GestionRessource.ResourceRequest.model.ResourceRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
  @Enumerated(EnumType.STRING)
  private ResourceType type;
  private String brand;
  private Integer quantity;
  private String specifications;

  @ManyToOne
  @JsonIgnoreProperties({"callForTender", "requestedProducts", "department", "teacher"})
  @JoinColumn(name = "resourceRequestId")
  private ResourceRequest resourceRequest;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "callForTenderId")
  @JsonIgnoreProperties({"proposals", "requestedProducts"})
  private CallForTender callForTender;
}
