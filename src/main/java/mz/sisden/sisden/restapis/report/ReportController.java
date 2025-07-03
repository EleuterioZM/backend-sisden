package mz.sisden.sisden.restapis.report;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.entities.Report;
import mz.sisden.sisden.restapis.exceptions.ResourceNotFoundException;
import mz.sisden.sisden.restapis.report.DTOs.ReportDTO;
import mz.sisden.sisden.restapis.report.DTOs.ReportResponseDTO;
import mz.sisden.sisden.services.report.ReportService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody ReportDTO dto) {
        try {
            Report saved = reportService.createReportWithFiles(dto); // método adaptado para base64
            return ResponseEntity.status(201).body(Map.of(
                    "message", "Report criado com sucesso",
                    "data", reportService.toResponseDTO(saved)
            ));
        } catch (IllegalArgumentException | NoSuchElementException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<ReportResponseDTO>> listAll() {
        List<ReportResponseDTO> response = reportService.getAll()
                .stream()
                .map(reportService::toResponseDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportResponseDTO> getById(@PathVariable Long id) {
        Report report = reportService.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report não encontrado"));
        return ResponseEntity.ok(reportService.toResponseDTO(report));
    }
}
