package fst.GestionRessource.Authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import fst.GestionRessource.Department.model.Department;
import fst.GestionRessource.User.model.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String fullName;
    private String userNumber;
    private String password;
    private List<Role> role;
    private Department department;
    private Department departmentHead;
}
