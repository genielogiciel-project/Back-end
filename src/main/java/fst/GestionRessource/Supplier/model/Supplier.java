package fst.GestionRessource.Supplier.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import fst.GestionRessource.Resource.model.Resource;
import fst.GestionRessource.User.model.Role;
import fst.GestionRessource.User.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
//@NoArgsConstructor
@RequiredArgsConstructor
public class Supplier extends User {
	private String companyName;
	private String address;
	private String website;
	private String managerName;
	private boolean blacklisted;
  private String blacklistReason;
	
	public Supplier(String fullName, String userNumber, String password, List<Role> role, String companyName, String address, String website, String managerName) {
		this.setFullName(fullName);
		this.setUserNumber(userNumber);
		this.setPassword(password);
		this.setRole(role);
		this.companyName = companyName;
		this.address = address;
		this.website = website;
		this.managerName = managerName;
		this.blacklisted = false;
		this.blacklistReason = null;
	}

  @JsonIgnoreProperties({"supplier"})
  @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Resource> resources;

}
