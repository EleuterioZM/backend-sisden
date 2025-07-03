package mz.sisden.sisden.restapis.reportClassification;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.entities.ReportClassification;
import mz.sisden.sisden.repositories.ReportClassificationRepository;
import mz.sisden.sisden.restapis.reportClassification.DTOs.ReportClassificationDTO;
import mz.sisden.sisden.restapis.reportClassification.DTOs.ReportClassificationResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/report-classification")
public class ReportClassificationController {

    private final ReportClassificationRepository reportClassificationRepository;

    @PostMapping
    public ResponseEntity<Map<String, String>> create(@Valid @RequestBody ReportClassificationDTO dto) {
        ReportClassification rc = ReportClassification.builder()
                .gravity(dto.getGravity())
                .build();

        reportClassificationRepository.save(rc);

        Map<String, String> response = new HashMap<>();
        response.put("message", "ReportClassification criado com sucesso!");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ReportClassificationResponseDTO>> listAll() {
        List<ReportClassification> list = reportClassificationRepository.findAll();
        List<ReportClassificationResponseDTO> dtoList = list.stream()
                .map(rc -> new ReportClassificationResponseDTO(
                        rc.getId(),
                        rc.getGravity(),
                        rc.getCreatedAt() != null ? rc.getCreatedAt().toString() : null
                )).toList();

        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<ReportClassification> optional = reportClassificationRepository.findById(id);
        if (optional.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "ReportClassification não encontrado");
            return ResponseEntity.status(404).body(response);
        }

        ReportClassification rc = optional.get();
        ReportClassificationResponseDTO dto = new ReportClassificationResponseDTO(
                rc.getId(),
                rc.getGravity(),
                rc.getCreatedAt() != null ? rc.getCreatedAt().toString() : null
        );

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ReportClassificationDTO dto) {
        Optional<ReportClassification> optional = reportClassificationRepository.findById(id);
        if (optional.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "ReportClassification não encontrado");
            return ResponseEntity.status(404).body(response);
        }

        ReportClassification rc = optional.get();
        rc.setGravity(dto.getGravity());

        reportClassificationRepository.save(rc);

        Map<String, String> response = new HashMap<>();
        response.put("message", "ReportClassification atualizado com sucesso!");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        if (!reportClassificationRepository.existsById(id)) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "ReportClassification não encontrado");
            return ResponseEntity.status(404).body(response);
        }

        reportClassificationRepository.deleteById(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "ReportClassification deletado com sucesso!");
        return ResponseEntity.ok(response);
    }
}
