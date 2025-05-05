package fst.GestionRessource.CallForTender.model;

import fst.GestionRessource.Proposal.model.Proposal;
import fst.GestionRessource.RequestedProduct.model.RequestedProduct;
import fst.GestionRessource.User.model.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CallRequest {
	private String id;
	private String title;
	private LocalDate startDate;
	private LocalDate endDate;
	private Boolean open;
	private User resourceManager;
	private List<RequestedProduct> requestedProducts;
//	private List<Proposal> proposals;
}
