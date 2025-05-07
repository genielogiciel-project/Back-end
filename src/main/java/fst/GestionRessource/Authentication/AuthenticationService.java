package fst.GestionRessource.Authentication;

import fst.GestionRessource.Supplier.repository.SupplierRepository;
import fst.GestionRessource.User.model.Role;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fst.GestionRessource.Configuration.JwtService;
import fst.GestionRessource.User.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    // private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final SupplierRepository supplierRepository;


    public ResponseEntity<?> authenticate(AuthenticationRequest request) {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              request.getUserNumber(),
              request.getPassword()));

      var user = repository.findByUserNumber(request.getUserNumber())
          .orElseThrow();
      if (user.getRole().contains(Role.SUPPLIER)) {
        var supplier = supplierRepository.findById(user.getId());
        if (supplier.isPresent()) {
          var supplierUser = supplier.get();
          if (supplierUser.isBlacklisted()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Vous etes en liste noir, vous n'avez pas l'authorization");
          }
        }
      }
      var jwtToken = jwtService.generateToken(user);
      //var refreshToken = jwtService.generateRefreshToken(user);
      //revokeAllUserTokens(user);
      //saveUserToken(user, jwtToken);
      return ResponseEntity.ok(AuthenticationResponse.builder()
          .accessToken(jwtToken)
          .user(user)
          //.refreshToken(refreshToken)
          .build());
    }

    public AuthenticationResponse refresh(AuthenticationRequest request) {
      var user = repository.findByUserNumber(request.getUserNumber()).orElseThrow();
      var jwtToken = jwtService.generateToken(user);


      return AuthenticationResponse.builder()
          .accessToken(jwtToken)
          .user(user)
          .build();
    }
}
