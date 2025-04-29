package fst.GestionRessource.Authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
      try {
        return ResponseEntity.ok(service.authenticate(request));
      } catch (Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

      }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.refresh(request));
    }

    @GetMapping("/hello")
    public ResponseEntity<?> hello() {
        return new ResponseEntity<>("<H1>HELLO WORLD</H1>", HttpStatus.OK);
    }
}
