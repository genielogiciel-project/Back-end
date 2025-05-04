package fst.GestionRessource.User.model;

import java.util.List;

import fst.GestionRessource.Department.model.Department;
import lombok.Data;

@Data
public class UserRequest {
  private String id;
  private Department department;
  private Department departmentHead;
  private List<Role> role;
}
