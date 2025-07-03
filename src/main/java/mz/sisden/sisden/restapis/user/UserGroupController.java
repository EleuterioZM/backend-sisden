/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.restapis.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.entities.UserGroup;
import mz.sisden.sisden.services.user.UserGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-groups")
@RequiredArgsConstructor
@Slf4j
public class UserGroupController {

    private final UserGroupService userGroupService;

    @GetMapping
    public ResponseEntity<List<UserGroup>> getAllUserGroups() {
        List<UserGroup> userGroups = userGroupService.findAll();
        return ResponseEntity.ok(userGroups);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserGroup> getUserGroupById(@PathVariable Long id) {
        return userGroupService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<UserGroup> getUserGroupByCode(@PathVariable String code) {
        return userGroupService.findByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserGroup> createUserGroup(@RequestBody UserGroup userGroup) {
        if (userGroupService.existsByCode(userGroup.getCode())) {
            return ResponseEntity.badRequest().build();
        }
        UserGroup savedUserGroup = userGroupService.save(userGroup);
        return ResponseEntity.ok(savedUserGroup);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserGroup> updateUserGroup(@PathVariable Long id, @RequestBody UserGroup userGroup) {
        return userGroupService.findById(id)
                .map(existingUserGroup -> {
                    userGroup.setId(id);
                    UserGroup updatedUserGroup = userGroupService.save(userGroup);
                    return ResponseEntity.ok(updatedUserGroup);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserGroup(@PathVariable Long id) {
        if (userGroupService.findById(id).isPresent()) {
            userGroupService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
} 