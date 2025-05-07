package fst.GestionRessource.ResourceRequest.controller;

import fst.GestionRessource.Department.model.Department;
import fst.GestionRessource.ResourceRequest.model.ResourceRequest;
import fst.GestionRessource.ResourceRequest.model.Status;
import fst.GestionRessource.ResourceRequest.service.ResourceRequestServiceImpl;
import fst.GestionRessource.User.model.User;
import fst.GestionRessource.User.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resource-request")
public class ResourceRequestController {
    private final ResourceRequestServiceImpl service;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllResourceRequests() {
        return service.getAllResourceRequests();
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<?> getAllResourceRequests(@PathVariable String userId) {
        return service.getAllResourceRequests(userId);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getResourceRequestById(@PathVariable String id) {
        return service.getResourceRequestById(id);
    }
    @PostMapping
    public ResponseEntity<?> addResourceRequest(@RequestBody ResourceRequest resourceRequest) {
      // System.out.println(resourceRequest);
      return service.addResourceRequest(resourceRequest);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateResourceRequest(@PathVariable String id,
        @RequestBody ResourceRequest resourceRequest) {

          System.out.println(resourceRequest);
        return service.updateResourceRequest(id, resourceRequest);
    }
    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateResourceRequestStatus(@PathVariable String id, @RequestBody Status status) {
        return service.updateResourceRequestStatus(id, status);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResourceRequest(@PathVariable String id) {
        return service.deleteResourceRequest(id);
    }
    //getting all the department requests
    @GetMapping("/by-dept/{userId}")
    public ResponseEntity<?> getResourceRequestsByDepartment(@PathVariable String userId) {
        ResponseEntity<?> temp = userService.getUser(userId);
        Department dept = temp.getBody() != null ? ((User) temp.getBody()).getDepartment() : null;

        return service.getResourceRequestByDept(dept);
    }
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<?> getResourceRequestsByUser(@PathVariable String userId) {
        ResponseEntity<?> temp = userService.getUser(userId);
        User user = temp.getBody() != null ? (User) temp.getBody() : null;

        return service.getResourceRequestByUser(user);
    }
}
