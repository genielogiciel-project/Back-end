package fst.GestionRessource.Department.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fst.GestionRessource.Department.model.Department;
import fst.GestionRessource.Department.service.DepartmentServiceImpl;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentController {
  private final DepartmentServiceImpl service;

  @GetMapping
  public ResponseEntity<?> getAllDepartments() {
    return service.getAllDepartments();
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getDepartmentById(@PathVariable String id) {
    return service.getDepartmentById(id);
  }

  @PostMapping
  public ResponseEntity<?> addDepartment(@RequestBody Department department) {
    return service.addDepartment(department);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateDepartment(@PathVariable String id, @RequestBody Department department) {
    return service.updateDepartment(id, department);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteDepartment(@PathVariable String id) {
    return service.deleteDepartment(id);
  }
}
