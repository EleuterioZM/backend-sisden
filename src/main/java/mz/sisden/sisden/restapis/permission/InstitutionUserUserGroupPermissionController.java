package mz.sisden.sisden.restapis.permission;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mz.sisden.sisden.entities.InstitutionUserUserGroupPermission;
import mz.sisden.sisden.entities.Instituition;
import mz.sisden.sisden.entities.User;
import mz.sisden.sisden.entities.UserGroup;
import mz.sisden.sisden.entities.Permission;
import mz.sisden.sisden.restapis.exceptions.ResourceNotFoundException;
import mz.sisden.sisden.services.permission.InstitutionUserUserGroupPermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/institution-user-group-permission")
public class InstitutionUserUserGroupPermissionController {
    private final InstitutionUserUserGroupPermissionService service;

    @GetMapping
    public ResponseEntity<List<InstitutionUserUserGroupPermission>> listAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/instituition/{instituitionId}")
    public ResponseEntity<List<InstitutionUserUserGroupPermission>> listByInstituition(@PathVariable Long instituitionId) {
        Instituition instituition = new Instituition();
        instituition.setId(instituitionId);
        return ResponseEntity.ok(service.findByInstituition(instituition));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<InstitutionUserUserGroupPermission>> listByUser(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);
        return ResponseEntity.ok(service.findByUser(user));
    }

    @GetMapping("/user-group/{userGroupId}")
    public ResponseEntity<List<InstitutionUserUserGroupPermission>> listByUserGroup(@PathVariable Long userGroupId) {
        UserGroup userGroup = new UserGroup();
        userGroup.setId(userGroupId);
        return ResponseEntity.ok(service.findByUserGroup(userGroup));
    }

    @GetMapping("/permission/{permissionId}")
    public ResponseEntity<List<InstitutionUserUserGroupPermission>> listByPermission(@PathVariable Long permissionId) {
        Permission permission = new Permission();
        permission.setId(permissionId);
        return ResponseEntity.ok(service.findByPermission(permission));
    }

    @PostMapping
    public ResponseEntity<InstitutionUserUserGroupPermission> create(@Valid @RequestBody InstitutionUserUserGroupPermission entity) {
        return ResponseEntity.status(201).body(service.save(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstitutionUserUserGroupPermission> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Permissão não encontrada"));
    }
} 