package fst.GestionRessource.User.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fst.GestionRessource.Authentication.RegisterRequest;
import fst.GestionRessource.User.model.User;
import fst.GestionRessource.User.service.UserService;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    @GetMapping()
    public ResponseEntity<List<User>> getAllUser(){
        return ResponseEntity.ok(service.getUsers());
    }

    @PostMapping()
    public ResponseEntity<?> addUser(@RequestBody RegisterRequest request) {
        return service.addUser(request);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id){
        return service.getUser(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        return service.deleteUser(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody RegisterRequest user) {
        return service.updateUser(id, user);
    }
    @GetMapping("/teachers")
    public ResponseEntity<?> getAllTeachers() {
        return service.getAllTeachers();
    }

    @GetMapping("/techs")
    public ResponseEntity<?> getAllTechs() {
        return service.getAllTechs();
    }
}
