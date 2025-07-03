package mz.sisden.sisden.restapis.permission;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mz.sisden.sisden.entities.Permission;
import mz.sisden.sisden.restapis.exceptions.ResourceNotFoundException;
import mz.sisden.sisden.services.permission.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/permissions")
public class PermissionController {
    private final PermissionService service;

    @GetMapping
    public ResponseEntity<List<Permission>> listAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Permission> findById(@PathVariable Long id) {
        Permission permission = service.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permissão não encontrada com id: " + id));
        return ResponseEntity.ok(permission);
    }

    @PostMapping
    public ResponseEntity<Permission> create(@Valid @RequestBody Permission entity) {
        return ResponseEntity.status(201).body(service.save(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Permission> update(@PathVariable Long id, @Valid @RequestBody Permission entity) {
        if (!service.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Permissão não encontrada com id: " + id);
        }
        entity.setId(id);
        return ResponseEntity.ok(service.save(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!service.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Permissão não encontrada com id: " + id);
        }
        service.delete(id);
        return ResponseEntity.ok().build();
    }
} 