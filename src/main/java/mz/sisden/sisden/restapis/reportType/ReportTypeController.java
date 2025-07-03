package mz.sisden.sisden.restapis.reportType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.entities.Instituition;
import mz.sisden.sisden.entities.ReportType;
import mz.sisden.sisden.repositories.InstituitionRepository;
import mz.sisden.sisden.repositories.ReportTypeRepository;
import mz.sisden.sisden.restapis.reportType.DTOs.ReportTypeDTO;
import mz.sisden.sisden.restapis.reportType.DTOs.ReportTypeResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/report-type")
public class ReportTypeController {

    private final ReportTypeRepository reportTypeRepository;
    private final InstituitionRepository instituitionRepository;

    @PostMapping
    public ResponseEntity<Map<String, String>> createReportType(@Valid @RequestBody ReportTypeDTO dto) {
        Optional<Instituition> instituitionOptional = instituitionRepository.findById(dto.getInstituitionId());
        if (instituitionOptional.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Instituição não encontrada");
            return ResponseEntity.status(404).body(response);
        }

        ReportType reportType = ReportType.builder()
                .name(dto.getName())
                .instituition(instituitionOptional.get())
                .build();

        reportTypeRepository.save(reportType);

        Map<String, String> response = new HashMap<>();
        response.put("message", "ReportType criado com sucesso!");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ReportTypeResponseDTO>> listAll() {
        List<ReportType> list = reportTypeRepository.findAll();
        List<ReportTypeResponseDTO> dtoList = list.stream()
                .map(rt -> new ReportTypeResponseDTO(
                        rt.getId(),
                        rt.getName(),
                        rt.getCreatedAt() != null ? rt.getCreatedAt().toString() : null,
                        rt.getInstituition() != null ? rt.getInstituition().getId() : null,
                        rt.getInstituition() != null ? rt.getInstituition().getName() : null
                ))
                .toList();

        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<ReportType> optional = reportTypeRepository.findById(id);
        if (optional.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "ReportType não encontrado");
            return ResponseEntity.status(404).body(response);
        }

        ReportType rt = optional.get();
        ReportTypeResponseDTO dto = new ReportTypeResponseDTO(
                rt.getId(),
                rt.getName(),
                rt.getCreatedAt() != null ? rt.getCreatedAt().toString() : null,
                rt.getInstituition() != null ? rt.getInstituition().getId() : null,
                rt.getInstituition() != null ? rt.getInstituition().getName() : null
        );

        return ResponseEntity.ok(dto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ReportTypeDTO dto) {
        Optional<ReportType> optional = reportTypeRepository.findById(id);
        if (optional.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "ReportType não encontrado");
            return ResponseEntity.status(404).body(response);
        }

        Optional<Instituition> instituitionOptional = instituitionRepository.findById(dto.getInstituitionId());
        if (instituitionOptional.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Instituição não encontrada");
            return ResponseEntity.status(404).body(response);
        }

        ReportType reportType = optional.get();
        reportType.setName(dto.getName());
        reportType.setInstituition(instituitionOptional.get());

        reportTypeRepository.save(reportType);

        Map<String, String> response = new HashMap<>();
        response.put("message", "ReportType atualizado com sucesso!");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        if (!reportTypeRepository.existsById(id)) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "ReportType não encontrado");
            return ResponseEntity.status(404).body(response);
        }

        reportTypeRepository.deleteById(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "ReportType deletado com sucesso!");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/instituition/{instituitionId}")
    public ResponseEntity<?> getByInstituitionId(@PathVariable Long instituitionId) {
        List<ReportType> reportTypes = reportTypeRepository.findByInstituitionId(instituitionId);

        if (reportTypes.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Nenhum ReportType encontrado para esta instituição");
            return ResponseEntity.status(404).body(response);
        }

        List<ReportTypeResponseDTO> dtoList = reportTypes.stream()
                .map(rt -> new ReportTypeResponseDTO(
                        rt.getId(),
                        rt.getName(),
                        rt.getCreatedAt() != null ? rt.getCreatedAt().toString() : null,
                        rt.getInstituition() != null ? rt.getInstituition().getId() : null,
                        rt.getInstituition() != null ? rt.getInstituition().getName() : null
                ))
                .toList();

        return ResponseEntity.ok(dtoList);
    }
}
