package fst.GestionRessource.Department.service;

import fst.GestionRessource.Department.model.Department;
import org.springframework.http.ResponseEntity;

public interface DepartementService {
   public ResponseEntity<?> getAllDepartments();

   public ResponseEntity<?> getDepartmentById(String id);

   public ResponseEntity<?> addDepartment(Department department);

   public ResponseEntity<?> updateDepartment(String id, Department department);

   public ResponseEntity<?> deleteDepartment(String id);
}
