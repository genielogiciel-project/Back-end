package fst.GestionRessource.User.service;

import fst.GestionRessource.Department.model.Department;
import fst.GestionRessource.Department.service.DepartmentService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fst.GestionRessource.Authentication.RegisterRequest;
import fst.GestionRessource.User.model.Role;
import fst.GestionRessource.User.model.User;
import fst.GestionRessource.User.repository.UserRepository;
import fst.GestionRessource.Utils.IdGenerator;
import fst.GestionRessource.Department.service.DepartmentServiceImpl;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    // @Autowired
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentServiceImpl DepService;

    public List<User> getUsers(){
        return repository.findAll();
    }
    public ResponseEntity<?> addUser(RegisterRequest request) {
        if (request.getRole().contains(Role.SUPER_ADMIN)) {
            return ResponseEntity.internalServerError().body("Super admin cannot be created.");
        }

        Optional<User> existingUser = repository.findByUserNumber(request.getUserNumber());

        if (existingUser.isPresent()) {
            return ResponseEntity.status(400).body("User with userNumber already exists.");
        }

        var ID = IdGenerator.generateId("U-");

        while (repository.existsById(ID)) {
            ID = IdGenerator.generateId("U-");
        }

        var user = User.builder()
                .id(ID)
                .userNumber(request.getUserNumber())
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .department(request.getDepartment())
                .departmentHead(request.getDepartmentHead())
                .build();

        repository.save(user);

        // Check if the user is a DEPARTMENT_HEAD
        if (request.getRole().contains(Role.DEPARTMENT_HEAD)) {
            if (user.getDepartment() != null) {
                Department department = user.getDepartment();
                department.setHead(user);
                department.setName(department.getName());
                // Save the updated department

                DepService.updateDepartment(department.getId(), department);
            } else {
                return ResponseEntity.status(400).body("Department must be specified for a DEPARTMENT_HEAD.");
            }
        }

        return ResponseEntity.ok("User created successfully");
    }

    public Object getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.isAuthenticated()) {
            return auth.getPrincipal();
        }
        return null;
    }

    public ResponseEntity<?> getUser(String id){
        User user = repository.findUserById(id);
        if(!repository.existsById(id))
                return ResponseEntity.status(404).body("User not found");

        return ResponseEntity.ok(user);
    }

    public ResponseEntity<?> deleteUser(String id) {
        if(!repository.existsById(id))
          return ResponseEntity.status(404).body("User not found");

        repository.deleteById(id);

        return ResponseEntity.ok("User deleted successfully");
    }

    public ResponseEntity<?> updateUser(String id, RegisterRequest user) {
      User existUser = repository.findUserById(id);
      Optional<User> existingUserNumber = repository.findByUserNumber(user.getUserNumber());

      if (existingUserNumber.isPresent() && existingUserNumber.get().getId() != id) {
        String message = "User with userNumber already exist.";

        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.internalServerError().body(response);
      }

      if (existUser != null) {
        if (user.getFullName() != null)
          existUser.setFullName(user.getFullName());
        if (user.getUserNumber() != null)
          existUser.setUserNumber(user.getUserNumber());
        if (user.getRole() != null)
          existUser.setRole(user.getRole());
        if (user.getPassword() != null && !user.getPassword().isEmpty())
          existUser.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(existUser);

        String message = "User updated successfully";
        Map<String, Object> response = new HashMap<>();
        response.put("user", existUser);
        response.put("message", message);

        return ResponseEntity.ok(response);
      } else {
        return ResponseEntity.status(404).body("User not found");
      }
    }

    public ResponseEntity<?> getAllTeachers() {
      var teachers = repository.findAll().stream().filter(user -> user.getRole().contains(Role.TEACHER)).collect(Collectors.toList());
      if (teachers.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No teachers found");
      }
      return ResponseEntity.ok(teachers);
    }
}
