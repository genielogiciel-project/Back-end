package fst.GestionRessource.Department.service;

public interface DepartementService {
   public ResponseEntity<?> getAllDepartments();

   public ResponseEntity<?> getDepartmentById(String id);

   public ResponseEntity<?> addDepartment(Department department);

   public ResponseEntity<?> updateDepartment(String id, Department department);

   public ResponseEntity<?> deleteDepartment(String id);
}
