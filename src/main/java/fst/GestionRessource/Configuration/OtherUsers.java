package fst.GestionRessource.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import fst.GestionRessource.Department.model.Department;
import fst.GestionRessource.User.model.Role;
import fst.GestionRessource.User.model.User;
import fst.GestionRessource.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OtherUsers implements ApplicationListener<ContextRefreshedEvent> {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (userRepository.count() >=6) return;
    var users = List.of(
      new User("U-00000000000000000000000000000002", "head", "chef de departement", passwordEncoder.encode("0"), Collections.singletonList(Role.DEPARTMENT_HEAD), null, null),
      new User("U-00000000000000000000000000000003", "teacher", "enseignant", passwordEncoder.encode("0"), Collections.singletonList(Role.TEACHER), null, null),
      new User("U-00000000000000000000000000000004", "supplier", "fournisseur", passwordEncoder.encode("0"), Collections.singletonList(Role.SUPPLIER), null, null),
      new User("U-00000000000000000000000000000005", "resmanager", "responsable des ressources", passwordEncoder.encode("0"), Collections.singletonList(Role.RESOURCE_MANAGER), null, null),
      new User("U-00000000000000000000000000000006", "tech", "technicien", passwordEncoder.encode("0"), Collections.singletonList(Role.TECHNICIAN), null, null)
    );

    userRepository.saveAll(users);
  }
}
