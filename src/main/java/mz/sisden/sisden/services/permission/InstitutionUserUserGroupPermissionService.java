package mz.sisden.sisden.services.permission;

import lombok.RequiredArgsConstructor;
import mz.sisden.sisden.entities.InstitutionUserUserGroupPermission;
import mz.sisden.sisden.entities.Instituition;
import mz.sisden.sisden.entities.User;
import mz.sisden.sisden.entities.UserGroup;
import mz.sisden.sisden.entities.Permission;
import mz.sisden.sisden.repositories.InstitutionUserUserGroupPermissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InstitutionUserUserGroupPermissionService {
    private final InstitutionUserUserGroupPermissionRepository repository;

    @Transactional(readOnly = true)
    public List<InstitutionUserUserGroupPermission> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public List<InstitutionUserUserGroupPermission> findByInstituition(Instituition instituition) {
        return repository.findAllByInstituition(instituition);
    }

    @Transactional(readOnly = true)
    public List<InstitutionUserUserGroupPermission> findByUser(User user) {
        return repository.findAllByUser(user);
    }

    @Transactional(readOnly = true)
    public List<InstitutionUserUserGroupPermission> findByUserGroup(UserGroup userGroup) {
        return repository.findAllByUserGroup(userGroup);
    }

    @Transactional(readOnly = true)
    public List<InstitutionUserUserGroupPermission> findByPermission(Permission permission) {
        return repository.findAllByPermission(permission);
    }

    @Transactional(readOnly = true)
    public List<InstitutionUserUserGroupPermission> findByInstituitionAndUserAndUserGroup(Instituition instituition, User user, UserGroup userGroup) {
        return repository.findAllByInstituitionAndUserAndUserGroup(instituition, user, userGroup);
    }

    @Transactional(readOnly = true)
    public List<InstitutionUserUserGroupPermission> findByInstituitionAndUserAndPermission(Instituition instituition, User user, Permission permission) {
        return repository.findAllByInstituitionAndUserAndPermission(instituition, user, permission);
    }

    @Transactional
    public InstitutionUserUserGroupPermission save(InstitutionUserUserGroupPermission entity) {
        return repository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<InstitutionUserUserGroupPermission> findById(Long id) {
        return repository.findById(id);
    }
} 