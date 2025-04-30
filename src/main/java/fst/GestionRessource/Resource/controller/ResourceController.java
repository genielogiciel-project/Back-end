package fst.GestionRessource.Resource.controller;

import fst.GestionRessource.Resource.model.Resource;
import fst.GestionRessource.Resource.service.ResourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping
    public ResponseEntity<List<Resource>> getAllResources() {
        return ResponseEntity.ok(resourceService.getAllResources());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getResourceById(@PathVariable String id) {
        return resourceService.getResourceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Resource> addResource(@RequestBody Resource resource) {
        return ResponseEntity.ok(resourceService.addResource(resource));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resource> updateResource(@PathVariable String id, @RequestBody Resource resource) {
        return resourceService.updateResource(id, resource)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResource(@PathVariable String id) {
        if (resourceService.deleteResource(id)) {
            return ResponseEntity.ok("Resource deleted successfully.");
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/by-user")
    public ResponseEntity<List<Resource>> getResourcesByUser(@RequestParam String userId) {
        return ResponseEntity.ok(resourceService.getResourcesByUser(userId));
    }
}