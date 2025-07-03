package mz.sisden.sisden.restapis.instituition;

import lombok.RequiredArgsConstructor;
import mz.sisden.sisden.entities.Instituition;
import mz.sisden.sisden.repositories.InstituitionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/instituitions")
public class InstituitionController {

    private final InstituitionRepository instituitionRepository;

    @GetMapping
    public List<InstituitionResponseDTO> listAll() {
        return instituitionRepository.findAll().stream()
                .map(i -> new InstituitionResponseDTO(
                        i.getId(),
                        i.getName(),
                        i.getCreatedAt() != null ? i.getCreatedAt().toString() : null
                ))
                .toList();
    }
} 