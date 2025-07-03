package mz.sisden.sisden.services.permission;

import lombok.RequiredArgsConstructor;
import mz.sisden.sisden.entities.Permission;
import mz.sisden.sisden.repositories.PermissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepository repository;

    @Transactional(readOnly = true)
    public List<Permission> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Permission save(Permission entity) {
        return repository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Permission> findById(Long id) {
        return repository.findById(id);
    }
} 