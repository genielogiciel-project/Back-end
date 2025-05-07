package fst.GestionRessource.Configuration;

import fst.GestionRessource.Authentication.RegisterRequest;
import fst.GestionRessource.Department.model.Department;
import fst.GestionRessource.Department.repository.DepartmentRepository;
import fst.GestionRessource.Department.service.DepartmentService;
import fst.GestionRessource.Supplier.model.Supplier;
import fst.GestionRessource.Supplier.repository.SupplierRepository;
import fst.GestionRessource.Supplier.service.SupplierService;
import fst.GestionRessource.User.model.Role;
import fst.GestionRessource.User.model.User;
import fst.GestionRessource.User.repository.UserRepository;
import fst.GestionRessource.User.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NeededObjects implements ApplicationListener<ContextRefreshedEvent> {
	private final UserService userService;
	private final UserRepository userRepository;
	private final DepartmentService departmentService;
	private final DepartmentRepository departmentRepository;
	private final SupplierService supplierService;
	private final SupplierRepository supplierRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (userRepository.count() > 0 || departmentRepository.count() > 0 || supplierRepository.count() > 0) {
			return ;
		}
		// Super Admin
		var super_admin = userRepository.findByUserNumber("00000").orElse(null);
		
		if (super_admin == null) {
			var user = User.builder()
				.id("U-00000000000000000000000000000001")
				.fullName("super admin")
				.userNumber("00000")
				.password(passwordEncoder.encode("0"))
				.role(Collections.singletonList(Role.SUPER_ADMIN))
				.build();
			userRepository.save(user);
		}
		
		// Other users
		var users = List.of(
			new RegisterRequest("chef de departement 1", "head1", "0", List.of(Role.DEPARTMENT_HEAD), null, null),
			new RegisterRequest("chef de departement 2", "head2", "0", List.of(Role.DEPARTMENT_HEAD), null, null),
			new RegisterRequest("enseignant 1", "teacher1", "0", List.of(Role.TEACHER), null, null),
			new RegisterRequest("enseignant 2", "teacher2", "0", List.of(Role.TEACHER), null, null),
			new RegisterRequest("enseignant 3", "teacher3", "0", List.of(Role.TEACHER), null, null),
			new RegisterRequest("enseignant 4", "teacher4", "0", List.of(Role.TEACHER), null, null),
			new RegisterRequest("enseignant 5", "teacher5", "0", List.of(Role.TEACHER), null, null),
			new RegisterRequest("responsable de resource", "resmanager", "0", List.of(Role.RESOURCE_MANAGER), null, null),
			new RegisterRequest("technicien 1", "tech1", "0", List.of(Role.TECHNICIAN), null, null),
			new RegisterRequest("technicien 2", "tech2", "0", List.of(Role.TECHNICIAN), null, null)
		);
		
		users.forEach(userService::addUser);
		
		var head1 = userRepository.findByUserNumber("head1").orElse(null);
		var head2 = userRepository.findByUserNumber("head2").orElse(null);
		var teacher1 = userRepository.findByUserNumber("teacher1").orElse(null);
		var teacher2 = userRepository.findByUserNumber("teacher2").orElse(null);
		var teacher3 = userRepository.findByUserNumber("teacher3").orElse(null);
		var teacher4 = userRepository.findByUserNumber("teacher4").orElse(null);
		var teacher5 = userRepository.findByUserNumber("teacher5").orElse(null);
		
		// Departments
		var departments = List.of(
			new Department(null, "Security", head1, List.of(teacher1, teacher2, teacher3), null, null),
			new Department(null, "Computer Science", head2, List.of(teacher4, teacher5), null, null),
			new Department(null, "Information Technology", null, null, null, null)
		);
		
		departments.forEach(departmentService::addDepartment);
		
		var security = departmentRepository.findByName("security").orElse(null);
		var computerScience = departmentRepository.findByName("Computer Science").orElse(null);
		
		teacher1 = userRepository.findByUserNumber("teacher1").orElse(null);
		teacher2 = userRepository.findByUserNumber("teacher2").orElse(null);
		teacher3 = userRepository.findByUserNumber("teacher3").orElse(null);
		teacher4 = userRepository.findByUserNumber("teacher4").orElse(null);
		teacher5 = userRepository.findByUserNumber("teacher5").orElse(null);
		
		teacher1.setDepartment(security);
		teacher2.setDepartment(security);
		teacher3.setDepartment(security);
		teacher4.setDepartment(computerScience);
		teacher5.setDepartment(computerScience);
		
		userRepository.saveAll(List.of(teacher1, teacher2, teacher3, teacher4, teacher5));
		
		var suppilers = List.of(
			new Supplier("fournisseur 1", "supplier1", "0", List.of(Role.SUPPLIER), "ASUS", "Fes", "https://example.com", "Mohammed Amine"),
			new Supplier("fournisseur 2", "supplier2", "0", List.of(Role.SUPPLIER), "DeLL", "Fes", "https://example.com", "Ouail"),
			new Supplier("fournisseur 3", "supplier3", "0", List.of(Role.SUPPLIER), "HP", "Fes", "https://example.com", "Ayoub")
		);
		
		suppilers.forEach(supplierService::addSupplier);
	}
}
