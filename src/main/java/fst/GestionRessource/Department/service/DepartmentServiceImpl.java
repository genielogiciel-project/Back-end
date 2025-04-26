package fst.GestionRessource.Department.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fst.GestionRessource.Department.model.Department;
import fst.GestionRessource.Department.repository.DepartmentRepository;
import fst.GestionRessource.Utils.IdGenerator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartementService {
  private final DepartmentRepository repository;

  public ResponseEntity<?> getAllDepartments() {
    return ResponseEntity.ok(repository.findAll());
  }

  public ResponseEntity<?> getDepartmentById(String id) {
    if (!repository.existsById(id)) {
      return ResponseEntity.status(404).body("Department not found");
    }
    return ResponseEntity.ok(repository.findById(id));
  }

  public ResponseEntity<?> addDepartment(Department department) {
    var existingDepartement = repository.findByName(department.getName().toLowerCase());
    if (existingDepartement.isPresent()) {
      return ResponseEntity.badRequest().body("Department with name " + department.getName() + " already exist.");
    }

    var ID = IdGenerator.generateId("DEPT-");

    while (repository.existsById(ID)) {
      ID = IdGenerator.generateId("DEPT-");
    }

    department.setId(ID);
    repository.save(department);

    return ResponseEntity.ok("Department created successfully");
  }

  public ResponseEntity<?> updateDepartment(String id, Department departement) {
    var existingDepartement = repository.findById(id);

    if (existingDepartement.isPresent()) {
      if (departement.getName() != null)
        existingDepartement.get().setName(departement.getName());
      if (departement.getHead() != null)
        existingDepartement.get().setHead(departement.getHead());

        repository.save(existingDepartement.get());
      return ResponseEntity.ok("Department updated successfully");
    }

    return ResponseEntity.status(404).body("Department not found");
  }

  public ResponseEntity<?> deleteDepartment(String id) {
    if (repository.existsById(id)) {
      repository.deleteById(id);
      return ResponseEntity.ok("Department deleted successfully");
    }

    return ResponseEntity.status(404).body("Department not found");
  }
}
