package mz.sisden.sisden.restapis.module;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mz.sisden.sisden.entities.Module;
import mz.sisden.sisden.restapis.exceptions.ResourceNotFoundException;
import mz.sisden.sisden.services.module.ModuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/modules")
public class ModuleController {
    private final ModuleService service;

    @GetMapping
    public ResponseEntity<List<Module>> listAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Module> findById(@PathVariable Long id) {
        Module module = service.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Módulo não encontrado com id: " + id));
        return ResponseEntity.ok(module);
    }

    @PostMapping
    public ResponseEntity<Module> create(@Valid @RequestBody Module entity) {
        return ResponseEntity.status(201).body(service.save(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Module> update(@PathVariable Long id, @Valid @RequestBody Module entity) {
        if (!service.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Módulo não encontrado com id: " + id);
        }
        entity.setId(id);
        return ResponseEntity.ok(service.save(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!service.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Módulo não encontrado com id: " + id);
        }
        service.delete(id);
        return ResponseEntity.ok().build();
    }
} 