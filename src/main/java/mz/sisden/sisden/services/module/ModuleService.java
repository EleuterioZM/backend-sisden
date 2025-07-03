package mz.sisden.sisden.services.module;

import lombok.RequiredArgsConstructor;
import mz.sisden.sisden.entities.Module;
import mz.sisden.sisden.repositories.ModuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModuleService {
    private final ModuleRepository repository;

    @Transactional(readOnly = true)
    public List<Module> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Module save(Module entity) {
        return repository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Module> findById(Long id) {
        return repository.findById(id);
    }
} 