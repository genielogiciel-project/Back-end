package fst.GestionRessource.Department.component;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import fst.GestionRessource.Department.model.Department;
import fst.GestionRessource.Department.service.DepartmentServiceImpl;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InitialDepartments implements ApplicationListener<ContextRefreshedEvent> {
  private final DepartmentServiceImpl service;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    List<Department> departments = new ArrayList<>(List.of(
      new Department(null, "Computer Science", null, null, null, null),
      new Department(null, "Electrical Engineering", null, null, null, null),
      new Department(null, "Mechanical Engineering", null, null, null, null),
      new Department(null, "Civil Engineering", null, null, null, null),
      new Department(null, "Mathematics", null, null, null, null),
      new Department(null, "Physics", null, null, null, null),
      new Department(null, "Chemistry", null, null, null, null),
      new Department(null, "Biology", null, null, null, null),
      new Department(null, "Security", null, null, null, null),
      new Department(null, "Environmental Science", null, null, null, null),
      new Department(null, "Information Technology", null, null, null, null),
      new Department(null, "Economics", null, null, null, null),
      new Department(null, "History", null, null, null, null),
      new Department(null, "English Language and Literature", null, null, null, null),
      new Department(null, "Philosophy", null, null, null, null),
      new Department(null, "Business Administration", null, null, null, null)
    ));

    if (((List<Department>) service.getAllDepartments().getBody()).isEmpty())
      for (Department department : departments) {
        service.addDepartment(department);
      }
  }
}
