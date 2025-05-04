package fst.GestionRessource.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
    if (userRepository.count() >= 10) return;
    var users = List.of(
      new User("U-00000000000000000000000000000002", "head1", "chef de departement 1", passwordEncoder.encode("0"), Collections.singletonList(Role.DEPARTMENT_HEAD), null, null, null, null),
      new User("U-00000000000000000000000000000003", "head2", "chef de departement 2", passwordEncoder.encode("0"), Collections.singletonList(Role.DEPARTMENT_HEAD), null, null, null, null),
      new User("U-00000000000000000000000000000004", "teacher1", "enseignant 1", passwordEncoder.encode("0"), Collections.singletonList(Role.TEACHER), null, null, null, null),
      new User("U-00000000000000000000000000000005", "teacher2", "enseignant 2", passwordEncoder.encode("0"), Collections.singletonList(Role.TEACHER), null, null, null, null),
      new User("U-00000000000000000000000000000006", "supplier1", "fournisseur 1", passwordEncoder.encode("0"), Collections.singletonList(Role.SUPPLIER), null, null, null, null),
      new User("U-00000000000000000000000000000007", "supplier2", "fournisseur 2", passwordEncoder.encode("0"), Collections.singletonList(Role.SUPPLIER), null, null, null, null),
      new User("U-00000000000000000000000000000008", "resmanager", "responsable des ressources", passwordEncoder.encode("0"), Collections.singletonList(Role.RESOURCE_MANAGER), null, null, null, null),
      new User("U-00000000000000000000000000000009", "tech1", "technicien 1", passwordEncoder.encode("0"), Collections.singletonList(Role.TECHNICIAN), null, null, null, null),
      new User("U-00000000000000000000000000000010", "tech2", "technicien 2", passwordEncoder.encode("0"), Collections.singletonList(Role.TECHNICIAN), null, null, null, null)
    );

    userRepository.saveAll(users);
  }
}
